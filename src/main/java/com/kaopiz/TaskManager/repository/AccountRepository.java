package com.kaopiz.TaskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByUsername(String username);
	Optional<Account> findByUsernameAndPassword(String username, String password);
}
