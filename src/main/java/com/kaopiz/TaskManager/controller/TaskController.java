package com.kaopiz.TaskManager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.payload.TaskDto;
import com.kaopiz.TaskManager.service.TaskService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin
@AllArgsConstructor
public class TaskController {
	
	private final TaskService  taskService;
	@GetMapping
	public List<TaskDto> getAllTask(){
		return taskService.listAll();
	}
	@PostMapping
	public TaskDto postTask(TaskDto taskRequest) {
		return taskService.save(taskRequest);
	}
}
