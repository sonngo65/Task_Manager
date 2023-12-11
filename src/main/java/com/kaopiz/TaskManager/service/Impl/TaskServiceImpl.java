package com.kaopiz.TaskManager.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.Task;
import com.kaopiz.TaskManager.payload.TaskDto;
import com.kaopiz.TaskManager.repository.TaskRepository;
import com.kaopiz.TaskManager.service.TaskService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{
	
	private final ModelMapper modelMapper;
	private final TaskRepository taskRepo;
	
	@Override
	public List<TaskDto> listAll() {
			return taskRepo.findAll().stream().map((task)-> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto save(TaskDto taskRequest) {
		Task task = modelMapper.map(taskRequest, Task.class);
		Task savedTask =  taskRepo.save(task);
		TaskDto taskResponse =  modelMapper.map(savedTask, TaskDto.class); 
		return taskResponse;
	}

}
