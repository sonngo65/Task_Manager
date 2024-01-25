package com.kaopiz.TaskManager.service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.entity.File;
import com.kaopiz.TaskManager.entity.Role;
import com.kaopiz.TaskManager.entity.Task;
import com.kaopiz.TaskManager.entity.TaskUser;
import com.kaopiz.TaskManager.entity.Workspace;
import com.kaopiz.TaskManager.entity.WorkspaceUser;
import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.payload.AccountDTO;
import com.kaopiz.TaskManager.payload.FileDTO;
import com.kaopiz.TaskManager.payload.RoleDTO;
import com.kaopiz.TaskManager.payload.TaskDTO;
import com.kaopiz.TaskManager.payload.WorkspaceCreateDTO;
import com.kaopiz.TaskManager.payload.WorkspaceDTO;
import com.kaopiz.TaskManager.repository.AccountRepository;
import com.kaopiz.TaskManager.repository.RoleRepository;
import com.kaopiz.TaskManager.repository.TaskUserRepository;
import com.kaopiz.TaskManager.repository.WorkspaceRepository;
import com.kaopiz.TaskManager.repository.WorkspaceUserRepository;
import com.kaopiz.TaskManager.service.WorkspaceService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class WorkspaceServiceImpl implements WorkspaceService {

	private final WorkspaceRepository workspaceRepo;
	private final AccountRepository accountRepo;
	private final ModelMapper modelMapper;
	private final TaskUserRepository taskUserRepo;
	private final WorkspaceUserRepository workspaceUserRepo;
	private final RoleRepository roleRepo;
	@Override
	public WorkspaceDTO save(WorkspaceCreateDTO workspaceRequest) {
		Random random = new Random();
		int codeInt = 1000000 + random.nextInt(999999);
		String code = String.valueOf(codeInt).substring(1);
		Workspace workspace = modelMapper.map(workspaceRequest, Workspace.class);
		Account user = accountRepo.findById(workspaceRequest.getUserId()).orElseThrow(
				() -> new ResourceNotFoundException("Not Found User By ID = " + workspaceRequest.getUserId()));
		WorkspaceUser workspaceUser = new WorkspaceUser();
		workspaceUser.setAccount(user);
		workspaceUser.setWorkspace(workspace);
		workspaceUser.setRole(roleRepo.findById((long) 3).get());
		workspace.setWorkspaceUsers(new HashSet<WorkspaceUser>());
		workspace.getWorkspaceUsers().add(workspaceUser);
		workspace.setCode(code);
		Workspace savedWorkplace = workspaceRepo.save(workspace);
		WorkspaceDTO workplaceResponse = modelMapper.map(savedWorkplace, WorkspaceDTO.class);
		workplaceResponse.setRoleName(workspaceUser.getRole().getName());
		return workplaceResponse;
	}

	@Override
	public WorkspaceDTO addUser(String workspaceCode, Long userId) {
		Workspace workspace = workspaceRepo.findByCode(workspaceCode)
				.orElseThrow(() -> new ResourceNotFoundException("not found workplace by id= " + workspaceCode));
		Account user = accountRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found User By Id= " + userId));
		WorkspaceUser workspaceUser = new WorkspaceUser();
		workspaceUser.setAccount(user);
		workspaceUser.setWorkspace(workspace);
		workspaceUser.setRole(roleRepo.findById((long) 1).get());
		workspace.getWorkspaceUsers().add(workspaceUser);
		workspaceRepo.save(workspace);
		WorkspaceDTO workspaceResponse = modelMapper.map(workspace, WorkspaceDTO.class);
		return workspaceResponse;
	}

	@Override
	public WorkspaceDTO findByWorkspaceIdAndUserId(Long workspaceId, Long userId) {
		Workspace workspace = workspaceRepo.findById(workspaceId)
				.orElseThrow(() -> new ResourceNotFoundException("not found workplace by id= " + workspaceId));
		WorkspaceDTO workspaceResponse = modelMapper.map(workspace, WorkspaceDTO.class);
		List<TaskUser> taskUsers = taskUserRepo.findAllByWorkspaceIdAndUserId(workspaceId, userId);
		List<TaskDTO> taskDtos = taskUsers.stream().map((taskUser) -> {
			TaskDTO taskDto = modelMapper.map(taskUser.getTask(), TaskDTO.class);
			taskDto.setProgress(taskUser.getProgress());
			return taskDto;

		}).toList();
		WorkspaceUser workspaceUser1 = workspaceUserRepo.findByWorkspaceIdAndUserId(workspaceId,userId);
		workspaceResponse.setRoleName(workspaceUser1.getRole().getName());
		workspaceResponse.setUsers(workspace.getWorkspaceUsers().stream().map((workspaceUser) -> {
			AccountDTO accountDTO = modelMapper.map(workspaceUser.getAccount(), AccountDTO.class);
			accountDTO.setRoleName(workspaceUser.getRole().getName());
			return accountDTO;
		}).toList());
		workspaceResponse.setTasks(taskDtos);

		return workspaceResponse;
	}

	@Override
	public List<WorkspaceDTO> findAllByUserId(Long id) {
		Account account = accountRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user id" + id));
		Set<WorkspaceUser> workspaceUsers = account.getWorkspaceUsers();
		List<WorkspaceDTO> workplacesResponse = workspaceUsers.stream()
				.map((workspaceUser) -> modelMapper.map(workspaceUser.getWorkspace() ,WorkspaceDTO.class)).toList();
		
		return workplacesResponse;
	}

	private TaskDTO mapTaskToTaskDto(Task task) {
		TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
		if (task.getUploadedFiles() == null) {

			taskDTO.setAssignerUploadedFiles(new ArrayList<FileDTO>());
		}
		return taskDTO;
	}

	@Override
	public WorkspaceDTO getFirst(Long userId) {
		List<Workspace> workspaces = workspaceRepo.findAllByUserId(userId);
		if (workspaces.size() > 0) {
			Long workspaceId = workspaces.get(0).getId();
			WorkspaceDTO workspaceDTO = findByWorkspaceIdAndUserId(workspaceId, userId);

			return workspaceDTO;
		}
		return null;

	}

	@Override
	public RoleDTO getRoleByFirstAndUserId( Long userId) {
		List<Workspace> workspaces = workspaceRepo.findAllByUserId(userId);
		if (workspaces.size() > 0) {
			Long workspaceId = workspaces.get(0).getId();
			WorkspaceUser workspaceUser = workspaceUserRepo.findByWorkspaceIdAndUserId(workspaceId, userId);
			Role role =workspaceUser.getRole();
			RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
			return roleDTO;
		}
		return null;
		
	}

	@Override
	public RoleDTO getRoleByWorkspaceIdAndUserId(Long workspaceId, Long userId) {
		WorkspaceUser workspaceUser = workspaceUserRepo.findByWorkspaceIdAndUserId(workspaceId, userId);
		Role role =workspaceUser.getRole();
		RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
		return roleDTO;
	}
}
