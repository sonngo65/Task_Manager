package com.kaopiz.TaskManager.security;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.repository.AccountRepository;

@Component
public class UserDetailsServiceCustom implements UserDetailsService{
		
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return accountRepo.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("not found uesrname"));
	}
	
}
