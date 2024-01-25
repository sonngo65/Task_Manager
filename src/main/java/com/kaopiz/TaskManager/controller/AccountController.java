package com.kaopiz.TaskManager.controller;

import java.util.List;
import java.util.Map;

import javax.naming.Binding;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.payload.AccountCreateDto;
import com.kaopiz.TaskManager.payload.AccountDTO;
import com.kaopiz.TaskManager.payload.ChangePasswordRequest;
import com.kaopiz.TaskManager.payload.ConfirmationCode;
import com.kaopiz.TaskManager.payload.ConfirmationCodeRequest;
import com.kaopiz.TaskManager.payload.EmailDTO;
import com.kaopiz.TaskManager.payload.LoginRequest;
import com.kaopiz.TaskManager.service.AccountService;
import com.kaopiz.TaskManager.service.EmailService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "Authorization Bearer")
public class AccountController {
	private AccountService accountServ;
	private EmailService emailService;
	@GetMapping
	public ResponseEntity<List<AccountDTO>> ListAllAccount(){
		List<AccountDTO> accountDTOList = accountServ.listAll();
		return new ResponseEntity<>(accountDTOList,HttpStatus.OK);
	}
	@PostMapping("/list")
	public ResponseEntity<List<AccountDTO>> addAccounts(@RequestBody List<AccountDTO> accountListRequest){
		List<AccountDTO> accountListResponse = accountServ.saveAll(accountListRequest);
		return new ResponseEntity<>(accountListResponse,HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<AccountDTO> addAccount(@Valid @RequestBody AccountCreateDto accountRequest){
	
		AccountDTO accountResponse = accountServ.save(accountRequest);
		return new ResponseEntity<>(accountResponse,HttpStatus.OK);
	}
	@GetMapping("/authentication")
	public ResponseEntity<AccountDTO> getAuthenticatedAccount(){
		AccountDTO authenticatedAccountResponse = accountServ.getAuthenticatedAccount();
		return new ResponseEntity<>(authenticatedAccountResponse,HttpStatus.OK);
	}
	 
	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> getUserById(@PathVariable Long id){
		AccountDTO accountResponse = accountServ.findById(id);
		return new ResponseEntity<>(accountResponse,HttpStatus.OK);
	}
	@GetMapping("/task-user/task/{id}")
	public ResponseEntity<List<AccountDTO>> getAllUserByTaskId(@PathVariable Long id){
		List<AccountDTO> accountResponses = accountServ.findByTaskId(id);
		return new ResponseEntity<>(accountResponses,HttpStatus.OK);
	}
	@GetMapping("/email")
	public ResponseEntity<Object> getEmail(@RequestParam("username") String username){
		AccountDTO accountDTO = accountServ.FindByUsername(username);
		EmailDTO emailResponse = new EmailDTO(accountDTO.getId(),accountDTO.getEmailAddress());
		return new ResponseEntity<>(emailResponse, HttpStatus.OK);
	}
	
	@PostMapping("/email/send-confirmation-code")
	public ResponseEntity<String> confirmEmail(@RequestBody EmailDTO emailRequest){
		emailService.sendConfirmationEmail(emailRequest.getAccountId(),emailRequest.getEmail());
		
		return new ResponseEntity<>("Lấy lại mật khẩu thành công" , HttpStatus.OK);
	}
	@PostMapping("/email/confirm-code")
	public ResponseEntity<Boolean> confirmCode(@RequestBody ConfirmationCodeRequest confirmationCodeRequest){
		return new ResponseEntity<>(emailService.confirmCode(confirmationCodeRequest),HttpStatus.OK);
	}
	@GetMapping("/email/confirmation-codes")
	public ResponseEntity<Map<Long,ConfirmationCode>> getAllConfirmationCode(){
		
		return new ResponseEntity<>(emailService.confirmationCodes,HttpStatus.OK);
	}
	@PutMapping
	public ResponseEntity<Boolean> deleteAll(@RequestBody List<AccountDTO> accountListRequest){
		Boolean isDeleted = accountServ.deleteAll(accountListRequest);
		return new ResponseEntity<>(isDeleted,HttpStatus.OK);
	}
	@PutMapping("/password")
	
	public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
		Boolean isChangedPassword = accountServ.changePassword(changePasswordRequest);
		return new ResponseEntity<>(isChangedPassword,HttpStatus.OK);
		
	}
	
}
