package com.kaopiz.TaskManager.service;

import java.util.List;

import com.kaopiz.TaskManager.payload.AccountCreateDto;
import com.kaopiz.TaskManager.payload.AccountDTO;
import com.kaopiz.TaskManager.payload.ChangePasswordRequest;
import com.kaopiz.TaskManager.payload.LoginRequest;

public interface AccountService {
	AccountDTO save(AccountCreateDto userRequest);
	AccountDTO findById(Long id);
	String getEmailByUsername(String username);
	AccountDTO FindByUsername(String username);
	Boolean changePassword(ChangePasswordRequest changePasswordRequest);
	AccountDTO getAuthenticatedAccount();
	List<AccountDTO> findByTaskId(Long id);
	List<AccountDTO> listAll();
	List<AccountDTO> saveAll(List<AccountDTO> accountListRequest);
	Boolean deleteAll(List<AccountDTO> accountListRequest);
}
