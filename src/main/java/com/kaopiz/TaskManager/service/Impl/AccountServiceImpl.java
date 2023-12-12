package com.kaopiz.TaskManager.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.entity.Role;
import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.payload.AccountDto;
import com.kaopiz.TaskManager.repository.AccountRepository;
import com.kaopiz.TaskManager.repository.RoleRepository;
import com.kaopiz.TaskManager.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
	private final AccountRepository accountRepo;
	private final RoleRepository roleRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	@Override
	public AccountDto save(AccountDto userRequest) {
		Account account = modelMapper.map(userRequest, Account.class);
		Role role = roleRepo.findById(userRequest.getRoleId()).orElseThrow(()-> new ResourceNotFoundException("not found role id"));
		account.setRole(role);
		account.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		Account savedAccount = accountRepo.save(account);
		AccountDto accountResponse = modelMapper.map(savedAccount, AccountDto.class);
		return accountResponse;
	}

}
