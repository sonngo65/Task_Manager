package com.kaopiz.TaskManager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(String name);

}
