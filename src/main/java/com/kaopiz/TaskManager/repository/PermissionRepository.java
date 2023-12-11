package com.kaopiz.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>{
	
}
