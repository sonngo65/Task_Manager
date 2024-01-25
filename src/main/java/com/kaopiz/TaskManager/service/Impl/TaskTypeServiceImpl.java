package com.kaopiz.TaskManager.service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.entity.TaskType;
import com.kaopiz.TaskManager.payload.TaskTypeDTO;
import com.kaopiz.TaskManager.repository.TaskTypeRepository;
import com.kaopiz.TaskManager.service.TaskTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskTypeServiceImpl implements TaskTypeService{
	private final TaskTypeRepository taskTypeRepo;
	private final ModelMapper modelMapper;
	@Override
	public List<TaskTypeDTO> listAll() {
		List<TaskType> taskTypes = taskTypeRepo.findAll();
		List<TaskTypeDTO> taskTypesResponse = taskTypes.stream().map((taskType)-> modelMapper.map(taskType, TaskTypeDTO.class)).toList(); 
		return taskTypesResponse;
	}

}
