package com.example.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.DTO.LoginRequest;

@RestController
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class LoginGoogle {

	@GetMapping("/login/google")
	public RedirectView loginWithGoogle() {
		return new RedirectView("/oauth2/authorization/google");
	}

	@GetMapping("/login/google/callback")
	public ResponseEntity<String> googleCallback(@RequestParam("code") String code) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("code", code);
		requestBody.add("client_id", "1012727703176-7bilg1agokg1824c95hc6sg6m481rnu8.apps.googleusercontent.com");
		requestBody.add("client_secret", "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");
		requestBody.add("redirect_uris", "https://clinicmates.io.vn/service");
		requestBody.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<OAuth2AccessTokenResponse> responseEntity = restTemplate.exchange(
				"https://oauth2.googleapis.com/token", HttpMethod.POST, requestEntity, OAuth2AccessTokenResponse.class);

		OAuth2AccessTokenResponse accessTokenResponse = responseEntity.getBody();

		OAuth2AccessToken accessToken = accessTokenResponse.getAccessToken();
		// Do something with the access token, such as storing it or using it for API
		// calls

		return ResponseEntity.ok("Xác thực thành công");
	}

	@GetMapping("/user")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
		LoginRequest userInfo = new LoginRequest();
		userInfo.setName(principal.getAttribute("name"));
		userInfo.setEmail(principal.getAttribute("email"));
		// Các thông tin khác về người dùng từ API Google

		return ResponseEntity.ok(userInfo);
	}

}