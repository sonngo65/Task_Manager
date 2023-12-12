package com.kaopiz.TaskManager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.payload.AccountDto;
import com.kaopiz.TaskManager.service.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor

@SecurityRequirement(name = "Authorization Bearer")
public class AccountController {
	private AccountService accountServ;
	
	@PostMapping
	public ResponseEntity<AccountDto> addAcount(@RequestBody AccountDto accountDto){
		AccountDto accountResponse = accountServ.save(accountDto);
		return new ResponseEntity<>(accountResponse,HttpStatus.OK);
	}
	
}
