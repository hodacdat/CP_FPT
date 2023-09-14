package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.PatientDTO;
import com.example.demo.DTO.PatientData;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.entity.Location;
import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;

@Service
public class PatientService implements UserDetailsService {

	@Autowired
	private PatientRepository repository;

	@Autowired
	private ImageService imageService;

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
		return Collections.singleton(new SimpleGrantedAuthority(role));
	}

	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		Patient painted = repository.findByEmail(username);
		if (painted == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(painted.getEmail(), painted.getPassword(),
				mapRolesToAuthorities(painted.getRole()));

	}

	public User loadUserByUsernamegoogle(String username) throws UsernameNotFoundException {

		Patient painted = repository.findByEmail(username);
		if (painted == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(painted.getEmail(), painted.getPassword(),
				mapRolesToAuthorities(painted.getRole()));

	}

	public String register(RegisterRequest request) {

		if (checkNameExists(request.getName()) != null) {
			return "Name already exists";
		}
		if (checkEmailExists(request.getEmail()) != null) {
			return "Email already exists";
		}
		if (checkIDExists(request.getId()) != null) {
			return "ID already exists";
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(request.getPassword());

		Patient p = new Patient(request.getId(), request.getName(), request.getEmail(), hashedPassword,
				request.getBirthdate(), "1430453.png");

		repository.save(p);
		return "Create success";
	}

	public String registergoogle(PatientData loginRequest) {

		if (checkNameExists(loginRequest.getDisplayName()) != null) {
			return "Name already exists";
		}
		if (checkEmailExists(loginRequest.getEmail()) != null) {
			return "Email already exists";
		}
		Patient p = new Patient();
		p.setEmail(loginRequest.getEmail());
		p.setName(loginRequest.getDisplayName());
		repository.save(p);
		return "Create success";
	}

	public String update(RegisterRequest request) {

		if (checkIDExists(request.getId()) == null) {
			return "Patient not exists";
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(request.getPassword());

		Patient p = new Patient(request.getId(), request.getName(), request.getEmail(), hashedPassword,
				request.getBirthdate());

		repository.save(p);
		return "Update success";
	}

	public String updateprofile(PatientDTO patientDTO, MultipartFile fileData) {

		if (checkIDExists(patientDTO.getId()) == null) {
			return "Patient not exists";
		}

		Patient p = checkIDExists(patientDTO.getId());

		p.setName(patientDTO.getName());
		p.setBirthDate(patientDTO.getBirthdate());
		p.setAddress(patientDTO.getAddress());
		p.setGender(patientDTO.getGender());
		p.setPhone(patientDTO.getPhone());
		p.setAvatar(patientDTO.getAvatar());
		if (fileData != null) {
			String avartar = imageService.uploadImage(fileData);

			if (!avartar.equals("Cannot upload file")) {
				if (!patientDTO.isAvatarEmptyOrNull()) {
					if (!patientDTO.getAvatar().equals("1430453.png")) {
						imageService.deleteImage(patientDTO.getAvatar());
					}
				}
				p.setAvatar(avartar);
			}
			if (avartar.equals("Cannot upload file")) {
				return "Update no success";
			}
		}
		if (!p.getEmail().equals(patientDTO.getEmail())) {
			p.setEmail(patientDTO.getEmail());
			p.setCommandFlag(0);
		}

		repository.save(p);
		return "Update success";
	}

	public String updatePassword(String email, String password) {
		Patient p = null;
		if (checkEmailExists(email) == null) {
			return "Patient not exists";
		}
		p = checkEmailExists(email);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		p.setPassword(hashedPassword);
		repository.save(p);
		return "Update success";
	}

	public String otpVerify(String email) {

		Patient painted = checkEmailExists(email);
		if (painted == null) {
			return "Email not exists";
		}
		painted.setCommandFlag(1);
		repository.save(painted);
		return "success";
	}

	public Patient findByEmail(String email) {
		return checkEmailExists(email);
	}

	private Patient checkEmailExists(String email) {
		return repository.findByEmail(email);
	}

	private Patient checkNameExists(String name) {
		return repository.findByName(name);
	}

	private Patient checkIDExists(String id) {
		return repository.findByID(id);
	}

	public Patient getProfileByEmail(String email) {
		// TODO Auto-generated method stub
		Patient p = null;
		p = repository.findByEmail(email);

		return p;
	}

	public List<Patient> findAll() {
		return repository.findPatients();
	}

	public List<Patient> findAllForAdmin() {
		return repository.findPatientsForAdmin();
	}
}