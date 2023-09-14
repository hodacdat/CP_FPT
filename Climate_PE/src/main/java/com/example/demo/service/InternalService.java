package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.CreateAccountDTO;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Role;
import com.example.demo.repository.InternalRepository;

@Service
public class InternalService implements UserDetailsService {

	@Autowired
	private InternalRepository internalRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private RoleService service;
	@Autowired
	private SpeciatlyService speciatlyService;

	@Autowired
	private LocationService locationService;

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
		return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<InternalAccount> internal = internalRepository.findByEmail(username);
		if (internal == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(internal.get().getEmail(),
				internal.get().getPassword(), mapRolesToAuthorities(internal.get().getRole()));
	}

	public List<InternalAccount> findAllDoctor() {
		return internalRepository.findAllDoctor();
	}

	public List<InternalAccount> findAllDoctorForAdmin() {
		return internalRepository.findAllDoctorForAdmin();
	}

	public List<InternalAccount> findAllDoctorWithLocation(int id) {
		return internalRepository.findAllDoctorByLocation(id);
	}

	public InternalAccount save(InternalAccount account) {
		System.out.println(account.toString());
		String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
		account.setPassword(hashedPassword);
		if (account.getAvatar().equals(null)) {
			account.setAvatar(
					"paramedic-avatar-clipart-icon-illustration-for-doctor-and-ners-medical-service-vector.jpg");
		}
		return internalRepository.save(account);
	}

	public InternalAccount update(InternalAccount account) {
		System.out.println(account.toString());
		InternalAccount c = internalRepository.findById(account.getId())
				.orElseThrow(() -> new EntityNotFoundException("User not found with id " + account.getId()));

		c.setEmail(account.getEmail());
		c.setPassword(account.getPassword());
		c.setName(account.getName());
		c.setBirthDate(account.getBirthDate());
		c.setGender(account.getGender());
		c.setPhone(account.getPhone());
		c.setYearOfExp(account.getYearOfExp());
		c.setEducation(account.getEducation());
		c.setAvatar(account.getAvatar());
		c.setIntroduct(account.getEducation());
		c.setAvatar(account.getAvatar());
		c.setWorkingPlace(account.getWorkingPlace());
		c.setRole(account.getRole());
		c.setSpecialty(account.getSpecialty());

		return internalRepository.save(c);
	}

	public List<InternalAccount> findAll() {
		return internalRepository.findAll();
	}

	public Optional<InternalAccount> findByEmail(String email) {
		return internalRepository.findByEmail(email);
	}

	public Optional<InternalAccount> findById(Integer id) {
		return internalRepository.findById(id);
	}

	public String findByIdUsingName(int id) {
		return internalRepository.getAccByIdWithnameDoctor(id);
	}

	public InternalAccount findByName(String doctorName, String location) {
		return internalRepository.findDoctor(doctorName, location);
	}

	public Optional<List<InternalAccount>> findDoctorBySpecialty(String specialty) {
		return internalRepository.findBySpecialty(specialty);
	}

	public Optional<List<InternalAccount>> searchInternalAccounts(String name, String specialty) {
		if (name != null && specialty != null) {
			return internalRepository.findByNameAndSpecialty(name, specialty);
		} else if (name != null) {
			return internalRepository.findByName(name);
		} else if (specialty != null) {
			return internalRepository.findBySpecialty(specialty);
		} else {
			return Optional.of(internalRepository.findAllDoctor());
		}
	}

	public String blockAccount(InternalAccount c) {
		c.setCommandFlag(2);
		internalRepository.save(c);
		return "success";
	}

	public String createprofile(CreateAccountDTO internalAccount, MultipartFile fileData) {
		if (checkEmailExists(internalAccount.getEmail()) > 0) {
			return "Email is exits.";
		}
		InternalAccount account = new InternalAccount();
		account.setEmail(internalAccount.getEmail());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(internalAccount.getPassword());
		account.setPassword(hashedPassword);
		account.setName(internalAccount.getName());
		account.setRole(service.findByIdac(Integer.parseInt(internalAccount.getRole())));
		account.setSpecialty(speciatlyService.findByIdAcc(Integer.parseInt(internalAccount.getSpecialty())));
		account.setWorkingPlace(locationService.findByIdAcc(internalAccount.getLocation()));
		if (fileData != null) {
			String avartar = imageService.uploadImage(fileData);
			if (!avartar.equals("Cannot upload file")) {
				account.setAvatar(avartar);
			}
			if (avartar.equals("Cannot upload file")) {
				return "Create no success";
			}
		} else {
			account.setAvatar(
					"paramedic-avatar-clipart-icon-illustration-for-doctor-and-ners-medical-service-vector.jpg");
		}
		internalRepository.save(account);
		return "Create success";
	}

	public String updateinter(CreateAccountDTO internalAccount) {
		if (checkIDExists(Integer.parseInt(internalAccount.getId())) == null) {
			return "Internal not exists";
		}
		InternalAccount account = checkIDExists(Integer.parseInt(internalAccount.getId()));
		account.setEmail(internalAccount.getEmail());
		account.setName(internalAccount.getName());
		account.setRole(service.findByIdac(Integer.parseInt(internalAccount.getRole())));
		account.setSpecialty(speciatlyService.findByIdAcc(Integer.parseInt(internalAccount.getSpecialty())));
		account.setWorkingPlace(locationService.findByIdAcc(internalAccount.getLocation()));
		account.setCommandFlag(Integer.parseInt(internalAccount.getCommandFlag()));
		internalRepository.save(account);
		return "Update success";
	}

	public String updateprofile(CreateAccountDTO internalAccount, MultipartFile fileData) {
		if (checkIDExists(Integer.parseInt(internalAccount.getId())) == null) {
			return "Internal not exists";
		}
		InternalAccount account = checkIDExists(Integer.parseInt(internalAccount.getId()));
		account.setEmail(internalAccount.getEmail());
		account.setName(internalAccount.getName());
		account.setBirthDate(internalAccount.getBirthdate());
		account.setPhone(internalAccount.getPhone());
		account.setIntroduct(internalAccount.getIntroduct());
		account.setGender(internalAccount.getGender());
		account.setEducation(internalAccount.getEducation());
		account.setYearOfExp(Integer.parseInt(internalAccount.getYearOfExp()));
		account.setRole(service.findByIdac(Integer.parseInt(internalAccount.getRole())));
		account.setSpecialty(speciatlyService.findByIdAcc(Integer.parseInt(internalAccount.getSpecialty())));
		account.setWorkingPlace(locationService.findByIdAcc(internalAccount.getLocation()));
		account.setAvatar(internalAccount.getAvatar());
		if (fileData != null) {
			String avartar = imageService.uploadImage(fileData);

			if (!avartar.equals("Cannot upload file")) {
				if (!account.isAvatarEmptyOrNull()) {
					if (!internalAccount.getAvatar().equals(
							"paramedic-avatar-clipart-icon-illustration-for-doctor-and-ners-medical-service-vector.jpg"))
						imageService.deleteImage(internalAccount.getAvatar());
				}
				account.setAvatar(avartar);
			}
			if (avartar.equals("Cannot upload file")) {
				return "Update no success";
			}
		}
		internalRepository.save(account);
		return "Update success";
	}

	private InternalAccount checkIDExists(int id) {
		return internalRepository.getAccById(id);
	}

	private int checkEmailExists(String email) {
		return internalRepository.getAccByEmailWithnameDoctor(email);
	}
}