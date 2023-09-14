package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.ApiResponse;
import com.example.demo.DTO.CountResult;
import com.example.demo.DTO.CreateAccountDTO;
import com.example.demo.DTO.CheckinDataDto;
import com.example.demo.DTO.CheckinDataDtoSpeAndDocTor;
import com.example.demo.DTO.InternalAccountDTO;
import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Specialty;
import com.example.demo.repository.InternalRepository;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.CheckinService;
import com.example.demo.service.InternalService;
import com.example.demo.service.JwtResponse;
import com.example.demo.service.JwtTokenUtil;
import com.example.demo.service.MedicalRecordService;
import com.example.demo.service.SpeciatlyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class InternalController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private InternalService internalService;

	@Autowired
	private CheckinService checkinService;

	@Autowired
	private MedicalRecordService medicalRecordService;
	@Autowired
	private InternalRepository repository;

	@Autowired
	private SpeciatlyService speciatlyService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private InternalService doctorService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> DoLogin(@RequestBody LoginRequest loginRequest) {
		System.out.println("url: " + loginRequest.getEmail() + loginRequest.getPassword());
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok("Incorrect email or password.");
		}
		Optional<InternalAccount> interacc = doctorService.findByEmail(email);
		if (!interacc.isPresent()) {
			return ResponseEntity.ok("Not found account.");
		}
		if (interacc.get().getCommandFlag() == 2) {
			return ResponseEntity.ok("You have no permission to login.");
		}
		final UserDetails userDetails = internalService.loadUserByUsername(email);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/save")
	public String save(@RequestBody InternalAccount account) {
		System.out.println("enter save: " + account.toString());
		internalService.save(account);
		return "success";
	}

	@PostMapping(value = "/createinter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String createinter(@RequestParam("internal") String internalJson,
			@RequestParam(value = "fileData", required = false) MultipartFile fileData) {
		CreateAccountDTO account = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			account = objectMapper.readValue(internalJson, CreateAccountDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Invalid JSON data for internal.";
		}
		String result = internalService.createprofile(account, fileData);

		return result;
	}

	@PostMapping(value = "/updateprofile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String update(@RequestParam("internal") String internalJson,
			@RequestParam(value = "fileData", required = false) MultipartFile fileData) {
		// Convert thông tin bệnh nhân từ JSON thành đối tượng PatientDTO
		CreateAccountDTO account = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			account = objectMapper.readValue(internalJson, CreateAccountDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Invalid JSON data for internal.";
		}
		String result = internalService.updateprofile(account, fileData);
		if (result.equals("Update success")) {

			final UserDetails userDetails = internalService.loadUserByUsername(account.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return token;
		}
		return result;
	}

	@PostMapping(value = "/updateinter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String editInter(@RequestParam("internal") String internalJson) {
		CreateAccountDTO account = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			account = objectMapper.readValue(internalJson, CreateAccountDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Invalid JSON data for internal.";
		}
		String result = internalService.updateinter(account);

		return result;
	}

	@PutMapping("/update")
	public String update(@RequestBody InternalAccount account) {
		System.out.println("enter save: " + account.toString());
		internalService.save(account);
		return "success";
	}

	@GetMapping("/list")
	public List<InternalAccount> getAll() {
		return internalService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<InternalAccount> getInternalById(@PathVariable(value = "id") Integer id) {
		Optional<InternalAccount> acc = internalService.findById(id);
		if (acc.isPresent()) {
			return ResponseEntity.ok().body(acc.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/block")
	public String blockAccount(@RequestParam(value = "id") String id) {
		System.out.println(id);
		InternalAccount acc = null;
		int idc = Integer.parseInt(id);
		acc = repository.getAccById(idc);
		if (acc != null) {
			String result = internalService.blockAccount(acc);
			if (result.equals("success")) {

				return "Block success";
			} else {
				return "Block fail";
			}
		} else {
			return "Not found account in system";
		}
	}

	@GetMapping(value = "/doctors")
	public List<InternalAccount> listAccDoctor() {
		return internalService.findAllDoctor();
	}

	@GetMapping(value = "/doctorsForAdmin")
	public List<InternalAccount> listAccDoctorForAdmin() {
		return internalService.findAllDoctorForAdmin();
	}

	@GetMapping(value = "/doctors/specialty")
	public List<InternalAccountDTO> listAccDoctorwithSpecialty() {
		List<InternalAccountDTO> responseList = new ArrayList<>();
		List<Specialty> specials = speciatlyService.findAll();
		for (Specialty special : specials) {
			Optional<List<InternalAccount>> doctors = internalService.findDoctorBySpecialty(special.getName());
			List<InternalAccount> doctorList = doctors.get();
			int count = doctorList.size();
			InternalAccountDTO response = new InternalAccountDTO(special.getName(), count, doctorList);
			responseList.add(response);

		}
		return responseList;
	}

	@GetMapping("/list_lo/{location}")
	public List<InternalAccount> getAllByLocation(@PathVariable int location) {
		return internalService.findAllDoctorWithLocation(location);
	}

	@GetMapping("/internal-accounts/search")
	public ResponseEntity<?> searchInternalAccounts(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "specialty", required = false) String specialty) {
		String role = "DOCTOR";
		Optional<List<InternalAccount>> accounts = internalService.searchInternalAccounts(name, specialty);
		if (accounts.isPresent() && !accounts.get().isEmpty()) {
			return ResponseEntity.ok(accounts);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No doctor information found"));
		}
	}

	@GetMapping("/internal-accounts/search-email")
	public ResponseEntity<?> searchByEmail(@RequestParam(value = "email") String email) {

		Optional<InternalAccount> accounts = internalService.findByEmail(email);
		if (accounts.isPresent()) {
			return ResponseEntity.ok(accounts);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No doctor information found"));
		}
	}

	@GetMapping("/count-in-current-month")
	public CountResult getCountOfRecordsInCurrentMonth() {
		int online = medicalRecordService.countRecordsInCurrentMonth();
		int completed = checkinService.countCommandFlag2ForCurrentMonth();
		int cancel = checkinService.countCommandFlag3ForCurrentMonth();
		int result = online + completed + cancel;
		double percentageOnline = (double) online / result * 100;
		double percentageCompleted = (double) completed / result * 100;
		double percentageCancel = (double) cancel / result * 100;
		return new CountResult(percentageOnline, percentageCompleted, percentageCancel);
	}

	@GetMapping("/countByDoctorOfData")
	public List<CheckinDataDto> countCheckinsByDoctor() {
		List<Object[]> countCheckinsByDoctor = checkinService.countCheckinsByDoctor();
		List<CheckinDataDto> checkinDataList = new ArrayList<>();

		// Converting the list of arrays to a list of CheckinData objects
		for (Object[] objArray : countCheckinsByDoctor) {
			CheckinDataDto checkinData = new CheckinDataDto();
			CheckinDataDto checkinDatacomple = new CheckinDataDto();
			String doctorIdStr = (String) objArray[0];
			Long count = (Long) objArray[1];

			// Parse the doctorId as an Integer
			Integer doctorId = Integer.parseInt(doctorIdStr);
			String doctorName = internalService.findByIdUsingName(doctorId);
			Long countcomplete = medicalRecordService.countOccurrencesByDoctorId(doctorIdStr);
			checkinData.setName(doctorName);
			checkinData.setNumber(count);
			checkinData.setType("Check-in");
			checkinDataList.add(checkinData);
			checkinDatacomple.setType("Completed");
			checkinDatacomple.setName(doctorName);
			checkinDatacomple.setNumber(countcomplete);
			checkinDataList.add(checkinDatacomple);
		}
		return checkinDataList;

	}

	@GetMapping("/countByDoctorOfApointment")
	public List<CheckinDataDtoSpeAndDocTor> countByDoctorOfApointment() {
		List<Object[]> countCheckinsByDoctor = checkinService.countCheckinsByDoctorAndSpecial();
		List<CheckinDataDtoSpeAndDocTor> checkinDataList = new ArrayList<>();

		// Converting the list of arrays to a list of CheckinData objects
		for (Object[] objArray : countCheckinsByDoctor) {
			CheckinDataDtoSpeAndDocTor checkinData = new CheckinDataDtoSpeAndDocTor();
			String doctorIdStr = (String) objArray[0];
			String special = (String) objArray[2];
			// Parse the doctorId as an Integer
			Integer doctorId = Integer.parseInt(doctorIdStr);
			String doctorName = internalService.findByIdUsingName(doctorId);
			Long countcomplete = medicalRecordService.countOccurrencesByDoctorId(doctorIdStr);
			Long appoiment = checkinService.countAppointmentByDoctor(doctorIdStr);
			String specialId = speciatlyService.findByName(special);
			checkinData.setNameDoctor(doctorName);
			checkinData.setExamination(countcomplete);
			checkinData.setId(doctorIdStr);
			checkinData.setNameSepcial(special);
			checkinData.setOnline(appoiment);
			checkinData.setIdspe(specialId);
			checkinDataList.add(checkinData);
		}
		return checkinDataList;

	}
}