package com.kaopiz.TaskManager.service.Impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.entity.File;
import com.kaopiz.TaskManager.entity.Status;
import com.kaopiz.TaskManager.entity.Task;
import com.kaopiz.TaskManager.entity.TaskType;
import com.kaopiz.TaskManager.entity.TaskUser;
import com.kaopiz.TaskManager.entity.UploadFile;
import com.kaopiz.TaskManager.entity.Workspace;
import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.payload.AccountDTO;
import com.kaopiz.TaskManager.payload.FileDTO;
import com.kaopiz.TaskManager.payload.TaskCreateDTO;
import com.kaopiz.TaskManager.payload.TaskDTO;
import com.kaopiz.TaskManager.payload.UploadFileDTO;
import com.kaopiz.TaskManager.repository.AccountRepository;
import com.kaopiz.TaskManager.repository.FileRepository;
import com.kaopiz.TaskManager.repository.StatusRepository;
import com.kaopiz.TaskManager.repository.TaskRepository;
import com.kaopiz.TaskManager.repository.TaskTypeRepository;
import com.kaopiz.TaskManager.repository.TaskUserRepository;
import com.kaopiz.TaskManager.repository.UploadFileRepository;
import com.kaopiz.TaskManager.repository.WorkspaceRepository;
import com.kaopiz.TaskManager.service.TaskService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

	private final ModelMapper modelMapper;
	private final TaskRepository taskRepo;
	private final AccountRepository accountRepo;
	private final WorkspaceRepository workspaceRepo;
	private final TaskUserRepository taskUserRepo;
	private final StatusRepository statusRepo;
	private final FileRepository fileRepo;
	private final UploadFileRepository uploadRepo;

	@Override
	public List<TaskDTO> listAll() {
		return taskRepo.findAll().stream().map((task) -> modelMapper.map(task, TaskDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public TaskDTO save(TaskCreateDTO taskRequest) {
		Task task = modelMapper.map(taskRequest, Task.class);
		Account assigner = accountRepo.findById(taskRequest.getAssignerId())
				.orElseThrow(() -> new ResourceNotFoundException("Not found user"));
		Workspace workspace = workspaceRepo.findById(taskRequest.getWorkspaceId()).orElseThrow(
				() -> new ResourceNotFoundException("Not Found Workspace by id = " + taskRequest.getWorkspaceId()));
		Status status = statusRepo.findById((long) 2)
				.orElseThrow(() -> new ResourceNotFoundException("Not found status id : " + 2));
		List<AccountDTO> members = taskRequest.getMembers();
		TaskUser taskUser = new TaskUser();
		task.setCreatedTime(new Date());
		task.setAssigner(assigner);
		task.setTaskUsers(new HashSet<TaskUser>());
		if (taskRequest.getParentTaskId() != null) {
			Task parentTask = taskRepo.findById(taskRequest.getParentTaskId()).orElseThrow(
					() -> new ResourceNotFoundException("Not found task by id = " + taskRequest.getParentTaskId()));
			task.setParentTask(parentTask);
			task.setUploadedFiles(taskRequest.getUploadedFiles().stream().map(fileDTO -> {
				File file = modelMapper.map(fileDTO, File.class);
				file.setTask(task);
				return file;
			}).toList());
		} else {
			taskUser.setWorkspace(workspace);
			taskUser.setAccount(assigner);
			taskUser.setTask(task);
			taskUser.setStatus(status);
			task.getTaskUsers().add(taskUser);
		}

		for (AccountDTO member : members) {
			taskUser = new TaskUser();
			taskUser.setAccount(modelMapper.map(member, Account.class));
			taskUser.setTask(task);
			taskUser.setStatus(status);

			taskUser.setWorkspace(workspace);
			task.getTaskUsers().add(taskUser);
		}

		Task savedTask = taskRepo.save(task);
		TaskDTO taskResponse = modelMapper.map(savedTask, TaskDTO.class);
		taskResponse.setAssignerUploadedFiles(
				savedTask.getUploadedFiles().stream().map((file) -> modelMapper.map(file, FileDTO.class)).toList());
		return taskResponse;
	}

	@Override
	public List<TaskDTO> findAllByUserIdAndParentTaskId(Long userId, Long parentTaskId) {
		List<TaskUser> taskUsers = taskUserRepo.findAllByUserIdAndParentTaskId(userId, parentTaskId);
		List<TaskDTO> tasksResponse = taskUsers.stream().map((taskUser) -> {
			TaskDTO taskDto = modelMapper.map(taskUser.getTask(), TaskDTO.class);
			if (taskUser.getStatus() != null)
				taskDto.setStatus(taskUser.getStatus().getName());
			if (taskUser.getUploadedFile() != null)
				taskDto.setMemberUploadedFile(modelMapper.map(taskUser.getUploadedFile(), UploadFileDTO.class));
			taskDto.setAssignerUploadedFiles(taskUser.getTask().getUploadedFiles().stream()
					.map((file) -> modelMapper.map(file, FileDTO.class)).toList());
			return taskDto;

		}).toList();

		return tasksResponse;
	}

	@Override
	public TaskCreateDTO findById(Long id) {
		Task task = taskRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Task by Id : " + id));
		TaskCreateDTO taskResponse = modelMapper.map(task, TaskCreateDTO.class);
		taskResponse.setMembers(task.getTaskUsers().stream()
				.map((taskUser) -> modelMapper.map(taskUser.getAccount(), AccountDTO.class)).toList());
		return taskResponse;
	}

	// get Task User by userId and taskId then get children task

	@Override
	public TaskDTO updateTask(Long childTaskId, Long userId, Long statusId) {
		TaskUser childTaskUser = taskUserRepo.findByUserIdAndTaskId(userId, childTaskId)
				.orElseThrow(() -> new ResourceNotFoundException(""));
		Status status = statusRepo.findById(statusId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found status id : " + statusId));
		childTaskUser.setStatus(status);
		TaskUser saveChildTaskUser = taskUserRepo.save(childTaskUser);
		TaskDTO taskResponse = modelMapper.map(saveChildTaskUser.getTask(), TaskDTO.class);
		taskResponse.setStatus(saveChildTaskUser.getStatus().getName());
		return taskResponse;
	}

	@Override
	public UploadFileDTO uploadFile(UploadFile uploadFile, Long userId, Long taskId) {
		TaskUser taskUser = taskUserRepo.findByUserIdAndTaskId(userId, taskId)
				.orElseThrow(() -> new ResourceNotFoundException(""));
		Date endTime = taskUser.getTask().getEndTime();
				
		uploadFile.setUploadedTime(new Date());
		
		if (taskUser.getUploadedFile() == null) {
			UploadFile savedUploadFile = uploadRepo.save(uploadFile);
			savedUploadFile.getFiles().forEach((file) -> {
				file.setUploadFile(savedUploadFile);
				fileRepo.save(file);
			});
			taskUser.setUploadedFile(savedUploadFile);
		} else {
			uploadFile.getFiles().forEach((file) -> {
				file.setUploadFile(taskUser.getUploadedFile());
				fileRepo.save(file);
			});
		}
		
		if(uploadFile.getUploadedTime().after(endTime)) {
			taskUser.setStatus(statusRepo.findById((long) 3).get());
		}
		else {
			taskUser.setStatus(statusRepo.findById((long) 1).get());
		}
		TaskUser updateTaskUser = 		taskUserRepo.save(taskUser);

		UploadFileDTO uploadFileDTO = modelMapper.map(updateTaskUser.getUploadedFile(), UploadFileDTO.class);

		uploadFileDTO.setTaskId(taskUser.getTask().getId());
		uploadFileDTO.setFiles(updateTaskUser.getUploadedFile().getFiles().stream()
				.map((file) -> modelMapper.map(file, FileDTO.class)).toList());
		uploadFileDTO.setStatus(taskUser.getStatus().getName());
		return uploadFileDTO;
	}

	@Override
	public TaskDTO getStatusTask(Long taskId, Long userId) {
		TaskUser childTaskUser = taskUserRepo.findByUserIdAndTaskId(userId, taskId)
				.orElseThrow(() -> new ResourceNotFoundException(""));
		TaskDTO taskResponse = modelMapper.map(childTaskUser.getTask(), TaskDTO.class);
		taskResponse.setStatus(childTaskUser.getStatus().getName());
		return taskResponse;
	}

	@Override
	public TaskDTO updateById(Long id,TaskDTO taskRequest) {
		Task task = taskRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("not found task Id : " + id));
		task.setContent(taskRequest.getContent());
		task.setDescription(task.getDescription());
		task.setEndTime(taskRequest.getEndTime());
		task.setStartTime(taskRequest.getStartTime());
		Task updatedTask = taskRepo.save(task);
		TaskDTO taskResponse = modelMapper.map(updatedTask, TaskDTO.class);
		return taskResponse;
	}

	@Override
	public TaskDTO deleteById(Long id) {
		Task task = taskRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("not found task Id : " + id));
		TaskDTO taskResponse = modelMapper.map(task, TaskDTO.class);
		

		return taskResponse;
	}

}
