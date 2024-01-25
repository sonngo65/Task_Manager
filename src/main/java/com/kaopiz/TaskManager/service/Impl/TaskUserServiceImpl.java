package com.kaopiz.TaskManager.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Statistic;
import com.kaopiz.TaskManager.entity.TaskUser;
import com.kaopiz.TaskManager.entity.Workspace;
import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.payload.AccountDTO;
import com.kaopiz.TaskManager.payload.ChildrenTaskUserDTO;
import com.kaopiz.TaskManager.payload.FileDTO;
import com.kaopiz.TaskManager.payload.ParentTaskUserDTO;
import com.kaopiz.TaskManager.payload.TaskDTO;
import com.kaopiz.TaskManager.payload.TaskUserDTO;
import com.kaopiz.TaskManager.payload.UploadFileDTO;
import com.kaopiz.TaskManager.repository.AccountRepository;
import com.kaopiz.TaskManager.repository.TaskUserRepository;
import com.kaopiz.TaskManager.repository.WorkspaceRepository;
import com.kaopiz.TaskManager.service.TaskUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskUserServiceImpl implements TaskUserService {
	private TaskUserRepository taskUserRepo;
	private AccountRepository accountRepo;
	private ModelMapper modelMapper;
	private WorkspaceRepository workspaceRepo;
	@Override
	public ChildrenTaskUserDTO findByTaskId(Long parentId, Long userId) {
		List<TaskUser> taskUsers = taskUserRepo.findAllByUserIdAndParentTaskId(userId, parentId);
		ChildrenTaskUserDTO childrenTaskUserDTO = new ChildrenTaskUserDTO();
		childrenTaskUserDTO.setUser(modelMapper.map(accountRepo.findById(userId).get(), AccountDTO.class));
		childrenTaskUserDTO.setChildrenTask(taskUsers.stream().map((taskUser) -> mapToTaskDTO(taskUser)).toList());
		return childrenTaskUserDTO;
	}

	@Override
	public List<ParentTaskUserDTO> findById(Long taskId) {
		List<TaskUser> taskUsers = taskUserRepo.findAllByTaskId(taskId);
		List<ParentTaskUserDTO> parentTaskUserDTOs = taskUsers.stream().map((taskUser) -> mapToParentTaskUserDTO(taskUser)).toList();

		return parentTaskUserDTOs;
	}

	@Override
	public List<TaskUserDTO> listAllByUserIdAndWorkspaceId(Long workspaceId, Long userId) {		
		List<TaskUser> taskUserList = taskUserRepo.findAllByWorkspaceIdAndUserId(workspaceId, userId);
		List<TaskUserDTO> taskUserDTOList = taskUserList.stream().map((taskUser)->{
			TaskUserDTO taskUserDTO = new TaskUserDTO();
			taskUserDTO.setParentTask(mapToParentTaskUserDTO(taskUser));
			taskUserDTO.setChildTask(findByTaskId(taskUser.getTask().getId(), taskUser.getAccount().getId()));
			return taskUserDTO;
		}).toList();
 		return taskUserDTOList;
	}
	public ParentTaskUserDTO mapToParentTaskUserDTO(TaskUser taskUser) {
		ParentTaskUserDTO parentTaskUserDTO = new ParentTaskUserDTO();
		parentTaskUserDTO.setUserId(taskUser.getAccount().getId());
		parentTaskUserDTO.setFirstName(taskUser.getAccount().getFirstName());
		parentTaskUserDTO.setLastName(taskUser.getAccount().getLastName());
		parentTaskUserDTO.setProgress(taskUser.getProgress());
		parentTaskUserDTO.setContent(taskUser.getTask().getContent());
		parentTaskUserDTO.setCompletedTime(taskUser.getCompletedTime());
		return parentTaskUserDTO;
	}
	public TaskDTO mapToTaskDTO(TaskUser taskUser) {
		TaskDTO taskDTO = modelMapper.map(taskUser.getTask(), TaskDTO.class);
		if(taskUser.getStatus() != null)
		taskDTO.setStatus(taskUser.getStatus().getName());
		if(taskUser.getUploadedFile() != null)
		taskDTO.setMemberUploadedFile(modelMapper.map(taskUser.getUploadedFile(),UploadFileDTO.class));
		taskDTO.setAssignerUploadedFiles(taskUser.getTask().getUploadedFiles().stream().map((file)-> modelMapper.map(file, FileDTO.class)).toList());

		return taskDTO;
	}

	@Override
	public List<TaskUserDTO> listAllByUserIdAndFirstWorkspace(Long userId) {
		List<Workspace> workspaces = workspaceRepo.findAllByUserId(userId);
		List<TaskUserDTO> taskUserDTO =  new ArrayList<TaskUserDTO>();
		if(workspaces.size() > 0) {
 		Long workspaceId = workspaces.get(0).getId();
 		
		return listAllByUserIdAndWorkspaceId(workspaceId, userId);
		}
		return null;
		}

	@Override
	public Statistic getStatisticByUserId( Long userId) {
		List<List<Integer>> responseData=  taskUserRepo.statisticTask( userId);
		Statistic statistic = null;
		if(responseData.size()>0) {
			List<Integer> taskStatistic = responseData.get(0);
			 statistic = new Statistic((long)taskStatistic.get(0),taskStatistic.get(1),taskStatistic.get(2),taskStatistic.get(3));

		}
		
		return statistic;
	}

	
}
