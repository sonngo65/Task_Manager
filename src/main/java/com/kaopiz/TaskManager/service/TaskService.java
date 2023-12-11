package com.kaopiz.TaskManager.service;

import java.util.List;

import com.kaopiz.TaskManager.payload.TaskDto;

public interface TaskService {
	List<TaskDto> listAll();
	TaskDto save(TaskDto taskRequest);
}
