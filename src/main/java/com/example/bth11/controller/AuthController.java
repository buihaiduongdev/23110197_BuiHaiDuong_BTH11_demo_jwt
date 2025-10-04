package com.example.bth11.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bth11.entity.User;
import com.example.bth11.model.LoginResponse;
import com.example.bth11.model.LoginUserModel;
import com.example.bth11.model.RegisterUserModel;
import com.example.bth11.service.AuthenticationService;
import com.example.bth11.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthController {

	private final JwtService jwtService;
	private final AuthenticationService authenticationService;

	public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
		User registeredUser = authenticationService.signup(registerUser);
		return ResponseEntity.ok(registeredUser);
	}

	@GetMapping("/login")
	public String index() {
		return "login";
	}

	@GetMapping("/user/profile")
	public String profile() {
		return "profile";
	}

	@PostMapping(path = "/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
		User authenticatedUser = authenticationService.authenticate(loginUser);
		String jwtToken = jwtService.generateToken(authenticatedUser);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		return ResponseEntity.ok(loginResponse);
	}
}