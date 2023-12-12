package com.kaopiz.TaskManager.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.payload.LoginRequest;
import com.kaopiz.TaskManager.payload.TokenResponse;
import com.kaopiz.TaskManager.security.JwtUtils;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
@AllArgsConstructor
public class SecurityController {
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		TokenResponse tokenResponse = null;
		if(authentication.isAuthenticated()) {
			tokenResponse = new TokenResponse("Success","Authentication successful",jwtUtils.generateToken(loginRequest.getUsername()),new Date(System.currentTimeMillis() + jwtUtils.getExpiration()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
		}
		tokenResponse = new TokenResponse("Error", "Authentication failed. Invalid username or password","",null);
		return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
	}
	
}
