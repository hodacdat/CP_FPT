package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.DTO.PatientData;
import com.example.demo.DTO.RegisterRequest;
import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Location;
import com.example.demo.entity.Patient;
import com.example.demo.mailHelper.MailDetail;
import com.example.demo.mailHelper.MailService;
import com.example.demo.mailHelper.OTP;
import com.example.demo.service.InternalService;
import com.example.demo.service.JwtResponse;
import com.example.demo.service.JwtTokenUtil;
import com.example.demo.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class PatientController {
	@Autowired
	private MailService mailService;

	private static List<OTP> otps = new ArrayList<OTP>();

	@Autowired
	private PatientService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private InternalService doctorService;
	@Autowired
	private PatientService patientService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		System.out.println("url: " + loginRequest.getEmail() + loginRequest.getPassword());
		System.out.println("patient");
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok("Incorrect email or password.");
		}
		Optional<InternalAccount> interacc = doctorService.findByEmail(email);
		Optional<Patient> pantiacc = Optional.ofNullable(patientService.findByEmail(email));
		if (interacc.isPresent() && !pantiacc.isPresent()) {
			return ResponseEntity.ok("Not found accout.");
		}
		final UserDetails userDetails = service.loadUserByUsername(email);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value = "/logingoogle")
	public ResponseEntity<?> logingoogle(@RequestBody PatientData loginRequest) {
		System.out.println("url: " + loginRequest.getEmail() + loginRequest.getDisplayName());
		System.out.println("patient");
		Optional<Patient> pantiacc = Optional.ofNullable(patientService.findByEmail(loginRequest.getEmail()));
		if (!pantiacc.isPresent()) {
			String result = service.registergoogle(loginRequest);
			final UserDetails userDetails = service.loadUserByUsernamegoogle(loginRequest.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new JwtResponse(token));
		}
		final UserDetails userDetails = service.loadUserByUsername(loginRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		System.out.println("url: " + request.getEmail() + request.getPassword());

		String result = service.register(request);
		if (result.equals("Create success")) {
			try {
				String OTP = generateOneTimePassword(request.getEmail());
				MailDetail m = new MailDetail(request.getEmail(), "OTP Verification", OTP);
				mailService.sendMail(m);
			} catch (UnsupportedEncodingException | MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResponseEntity.ok(result);

	}

	@PostMapping(value = "/update")
	public ResponseEntity<?> update(@RequestBody RegisterRequest request) {

		String result = service.update(request);

		return ResponseEntity.ok(result);

	}

	@PostMapping(value = "/updateprofile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(@RequestParam("patient") String patientJson,
			@RequestParam(value = "fileData", required = false) MultipartFile fileData) {
		// Convert thông tin bệnh nhân từ JSON thành đối tượng PatientDTO
		PatientDTO patientDTO = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			patientDTO = objectMapper.readValue(patientJson, PatientDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Invalid JSON data for patient.");
		}

		// Tiến hành xử lý thông tin bệnh nhân và fileData
		String result = service.updateprofile(patientDTO, fileData);

		if (result.equals("Update success")) {

			final UserDetails userDetails = service.loadUserByUsername(patientDTO.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(token);
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/resend")
	public String resendOTP(@RequestParam("email") String email) {
		System.out.println("email in resend: " + email);
		// remove old otp
		if (!otps.isEmpty()) {
			for (OTP otp : otps) {
				if (otp.getEmail().equals(email)) {
					otps.remove(otp);
				}
			}
		}

		// resend new otp
		try {
			String OTP = generateOneTimePassword(email);
			MailDetail m = new MailDetail(email, "OTP Verification", OTP);
			mailService.sendMail(m);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";

	}

	@GetMapping("/forgot")
	public String forgotPass(@RequestParam("email") String email) {
		System.out.println(email);
		Patient p = null;
		p = service.findByEmail(email);
		if (p == null) {
			return "Not found patient with this email";
		} else {
			String result = resendOTP(email);
			if (result.equals("success")) {
				return "Send OTP success";
			} else {
				return "Send OTP fail";
			}
		}
	}

	/**
	 * Check OTP of a user.
	 *
	 * @param EnteredOtp the entered otp
	 * @param email      the email
	 * 
	 * @return the string
	 */
	@GetMapping("/checkotp")
	public String checkOTP(@RequestParam("otp") String EnteredOtp, @RequestParam("email") String email) {
		OTP o1 = null;
		for (OTP otp : otps) {
			if (otp.getEmail().equals(email) && otp.getContent().equals(EnteredOtp)) {
				o1 = otp;
			}
		}

		if (null == o1) {
			return "Fail to check otp";
		}

		boolean exp = checkTimeOTP(o1.getExpiredTime());
		System.out.println("before remove");
		for (OTP otp : otps) {
			System.out.println(otp.getEmail());
		}
		otps.remove(o1);

		System.out.println("after remove");
		for (OTP otp : otps) {
			System.out.println(otp.getEmail());
		}
		if (!exp) {
			// otp right
			service.otpVerify(email);
			return "verify success";
		} else {
			return "OTP expired!!";
		}
	}

	@GetMapping("/checkotpforgot")
	public String checkOTPForgot(@RequestParam("otp") String EnteredOtp, @RequestParam("email") String email) {
		OTP o1 = null;
		for (OTP otp : otps) {
			if (otp.getEmail().equals(email) && otp.getContent().equals(EnteredOtp)) {
				o1 = otp;
			}
		}

		if (null == o1) {
			return "Fail to check otp";
		}

		boolean exp = checkTimeOTP(o1.getExpiredTime());
		System.out.println("before remove");
		for (OTP otp : otps) {
			System.out.println(otp.getEmail());
		}
		otps.remove(o1);

		System.out.println("after remove");
		for (OTP otp : otps) {
			System.out.println(otp.getEmail());
		}
		if (!exp) {
			// otp right
			return "verify success";
		} else {
			return "OTP expired!!";
		}
	}

	@PutMapping("/updatepassword")
	public String updateNewPass(@RequestParam("email") String email, @RequestParam("newpass") String newPass) {
		String result = service.updatePassword(email, newPass);
		if (result.equals("Update success")) {
			return "Update success";
		} else {
			return result;
		}
	}

	@GetMapping("/profile")
	public Patient getProfile(@RequestParam("email") String email) {
		return service.getProfileByEmail(email);
	}

	/**
	 * Generate OTP for an user with user's email.
	 *
	 * @param email the email
	 * 
	 * @return the OTP string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws MessagingException           the messaging exception
	 */
	public String generateOneTimePassword(String email) throws UnsupportedEncodingException, MessagingException {
		String OTP = RandomString.make(8);
		long expireAt = getOtpExpiredTime();
		OTP otp2 = new OTP(email, OTP, expireAt);

		int index = -1;
		for (OTP otpFind : otps) {
			if (otpFind.getEmail().equals(email)) {
				index = otps.indexOf(otpFind);
			}
		}

		if (index > -1) {
			otps.set(index, otp2);
		} else {
			otps.add(otp2);
		}
		return OTP;
	}

	/**
	 * Gets the otp expired time.
	 *
	 * @return the otp expired time
	 */
	public long getOtpExpiredTime() {
		long expiredAt = new Date().getTime() + TimeUnit.MINUTES.toMillis(2);
		return expiredAt;
	}

	/**
	 * Check OTP is expired or not.
	 *
	 * @param expiredAt the expired at
	 * @return true if time is expired
	 */
	public boolean checkTimeOTP(long expiredAt) {
		long currentTime = new Date().getTime();
		if (expiredAt - currentTime > 0) {
			return false;
		}
		return true;
	}

	@GetMapping("/list")
	public List<Patient> getAll() {
		return service.findAll();
	}

	@GetMapping("/listadmin")
	public List<Patient> getAllForAdmin() {
		return service.findAllForAdmin();
	}

}
