package com.kaopiz.TaskManager.service;

import java.util.List;

import com.kaopiz.TaskManager.payload.RoleDTO;
import com.kaopiz.TaskManager.payload.WorkspaceCreateDTO;
import com.kaopiz.TaskManager.payload.WorkspaceDTO;

public interface WorkspaceService {
	public WorkspaceDTO findByWorkspaceIdAndUserId(Long workspaceId,Long userId);
	public WorkspaceDTO save(WorkspaceCreateDTO workspaceRequest);
	public WorkspaceDTO addUser(String workspaceCode,Long userId);
	public List<WorkspaceDTO> findAllByUserId(Long id);
	public WorkspaceDTO getFirst(Long userId);
	public RoleDTO getRoleByFirstAndUserId( Long userId);
	public RoleDTO getRoleByWorkspaceIdAndUserId(Long workspaceId ,Long userId);

}
