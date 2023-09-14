package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Patient;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private InternalService doctorService;
	@Autowired
	private PatientService patientService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<InternalAccount> interacc = doctorService.findByEmail(username);
		Optional<Patient> pantiacc = Optional.ofNullable(patientService.findByEmail(username));
		if (interacc.isPresent()) {
			// Kiểm tra trong doctorService
			UserDetails doctorUser = doctorService.loadUserByUsername(username);
			if (doctorUser != null) {
				return doctorUser;
			}

		}
		if (pantiacc.isPresent()) {
			UserDetails patientUser = patientService.loadUserByUsername(username);
			if (patientUser != null) {
				return patientUser;
			}
		}

		throw new UsernameNotFoundException("User not found.");
	}
}
