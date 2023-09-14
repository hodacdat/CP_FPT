package com.example.demo.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entity.InternalAccount;
import com.example.demo.entity.Patient;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtil {
	private final String secret = "mySecret";

	@Autowired
	private InternalService internalService;

	@Autowired
	private PatientService patientService;

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		claims.put("roles", authorities);
		Optional<InternalAccount> interacc = internalService.findByEmail(userDetails.getUsername());
		Optional<Patient> pantiacc = Optional.ofNullable(patientService.findByEmail(userDetails.getUsername()));
		if (pantiacc.isPresent()) {
			claims.put("nameUser", pantiacc.get().getName());
		}
		if (interacc.isPresent()) {
			claims.put("nameInternal", interacc.get().getName());
		}
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String email = extractEmail(token);
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		final Date expiration = extractExpiration(token);
		return expiration.before(new Date());
	}

	private Date extractExpiration(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
	}

	private String extractEmail(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
}