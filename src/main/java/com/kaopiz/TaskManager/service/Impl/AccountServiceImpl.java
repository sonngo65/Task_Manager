package com.kaopiz.TaskManager.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.entity.Role;
import com.kaopiz.TaskManager.entity.TaskUser;
import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.payload.AccountCreateDto;
import com.kaopiz.TaskManager.payload.AccountDTO;
import com.kaopiz.TaskManager.payload.ChangePasswordRequest;
import com.kaopiz.TaskManager.payload.LoginRequest;
import com.kaopiz.TaskManager.repository.AccountRepository;
import com.kaopiz.TaskManager.repository.RoleRepository;
import com.kaopiz.TaskManager.repository.TaskUserRepository;
import com.kaopiz.TaskManager.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepo;
	private final RoleRepository roleRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final TaskUserRepository taskUserRepo;

	@Override
	public AccountDTO save(AccountCreateDto userRequest) {
		Account account = modelMapper.map(userRequest, Account.class);
		Role role = roleRepo.findByName("USER").orElseThrow(() -> new ResourceNotFoundException("not found role Name"));
		account.setRole(role);
		account.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		Account savedAccount = accountRepo.save(account);
		AccountDTO accountResponse = modelMapper.map(savedAccount, AccountDTO.class);
		return accountResponse;
	}

	@Override
	public String getEmailByUsername(String username) {
		Account account = accountRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(username));
		return account.getEmailAddress();
	}

	@Override
	public AccountDTO FindByUsername(String username) {
		Account account = accountRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(username));
		AccountDTO accountResponse = modelMapper.map(account, AccountDTO.class);
		return accountResponse;
	}

	@Override
	public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
		Optional<Account> savedAccount = accountRepo.findById(changePasswordRequest.getAccountId());
		if (savedAccount.isEmpty()) {
			throw new ResourceNotFoundException("change password is wrong !");
		}
		Account account = savedAccount.get();
		account.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));

		accountRepo.save(account);
		return true;
	}

	@Override
	public AccountDTO getAuthenticatedAccount() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() == null) {
			throw new AccessDeniedException("Token invalid");
		}
		Account account = accountRepo.findByUsername(auth.getPrincipal().toString())
				.orElseThrow(() -> new ResourceNotFoundException("Not Found User "));
		AccountDTO authenticatedAccountResponse = modelMapper.map(account, AccountDTO.class);
		return authenticatedAccountResponse;
	}

	@Override
	public AccountDTO findById(Long id) {
		Account account = accountRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user by Id : " + id));
		AccountDTO accountResponse = modelMapper.map(account, AccountDTO.class);

		return accountResponse;
	}

	@Override
	public List<AccountDTO> findByTaskId(Long id) {
		List<TaskUser> taskUsers = taskUserRepo.findAllByTaskId(id);
		List<AccountDTO> accounts = taskUsers.stream()
				.map((taskUser) -> modelMapper.map(taskUser.getAccount(), AccountDTO.class)).toList();
		return accounts;
	}

	@Override
	public List<AccountDTO> listAll() {
		List<Account> accountList = accountRepo.findAll();
		List<AccountDTO> accountDTOList = accountList.stream()
				.map((account) -> modelMapper.map(account, AccountDTO.class)).toList();
		return accountDTOList;
	}

	@Override
	public List<AccountDTO> saveAll(List<AccountDTO> accountListRequest) {
		List<Account> accounts = accountListRequest.stream().map(accountDTO -> {
			Account account = modelMapper.map(accountDTO, Account.class);
			account.setPassword(passwordEncoder.encode("123"));

			if(accountDTO.getId()!=null) {
				Account addedAccount = accountRepo.findById(accountDTO.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Not found user by Id : " + accountDTO.getId()));
				account.setPassword(addedAccount.getPassword());
				account.setRole(addedAccount.getRole());
			}
		
		
			return account;
		}).toList();
		List<Account> savedAccounts = accountRepo.saveAll(accounts);
		List<AccountDTO> accountListResponse = savedAccounts.stream()
				.map(savedAccount -> modelMapper.map(savedAccount, AccountDTO.class)).toList();

		return accountListResponse;
	}

	@Override
	public Boolean deleteAll(List<AccountDTO> accountListRequest) {
		List<Account> accounts = accountListRequest.stream().map(accountDTO -> {
			Account account = modelMapper.map(accountDTO, Account.class);
			return account;
		}).toList();		
		try {
		accountRepo.deleteAll(accounts);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

}
