package com.kaopiz.TaskManager.service.Impl;

import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Permission;
import com.kaopiz.TaskManager.repository.PermissionRepository;
import com.kaopiz.TaskManager.service.PermissionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService{
	
	private final PermissionRepository permissionRepo;
	
	@Override
	public Permission save(Permission permission) {
		Permission savedPermission = permissionRepo.save(permission);
		return savedPermission;
	}

}
