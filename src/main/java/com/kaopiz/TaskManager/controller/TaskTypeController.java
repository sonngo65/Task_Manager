package com.kaopiz.TaskManager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.payload.TaskTypeDTO;
import com.kaopiz.TaskManager.service.TaskTypeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/task-type")
@CrossOrigin
@AllArgsConstructor
public class TaskTypeController {
	private final TaskTypeService taskTypeServ;
	@GetMapping
	public ResponseEntity<List<TaskTypeDTO>> getAllTaskType(){
		List<TaskTypeDTO> taskTypesResponse = taskTypeServ.listAll();
		return new ResponseEntity<>(taskTypesResponse,HttpStatus.OK);
	}
	
}
