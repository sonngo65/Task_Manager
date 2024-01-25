package com.kaopiz.TaskManager.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.entity.Statistic;
import com.kaopiz.TaskManager.payload.ChildrenTaskUserDTO;
import com.kaopiz.TaskManager.payload.ParentTaskUserDTO;
import com.kaopiz.TaskManager.payload.TaskUserDTO;
import com.kaopiz.TaskManager.repository.TaskUserRepository;
import com.kaopiz.TaskManager.service.TaskUserService;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/task-user")
@AllArgsConstructor
@CrossOrigin
@Log4j2
public class TaskUserController {
	private final TaskUserService taskUserServ;

	@GetMapping("/task/{id}")
	public ResponseEntity<List<ParentTaskUserDTO>> getAllParentTaskUserByTaskId(@PathVariable Long id){
		List<ParentTaskUserDTO> parentTaskUserDTOs = taskUserServ.findById(id);
		return new ResponseEntity<>(parentTaskUserDTOs,HttpStatus.OK);
	}
	@GetMapping("/children-task")
	public ResponseEntity<ChildrenTaskUserDTO> getChildrentTaskUser(@RequestParam("parentTaskId") Long parentTaskId,@RequestParam("userId") Long userId){

		ChildrenTaskUserDTO childrenTaskUserDTO  =taskUserServ.findByTaskId(parentTaskId, userId);
		return new ResponseEntity<>(childrenTaskUserDTO, HttpStatus.OK );
	}
	
	@GetMapping("/workspace/{workspaceId}/user/{userId}")
	public ResponseEntity<List<TaskUserDTO>> getAllTaskUserByWorkspaceIdAndUserId(@PathVariable Long workspaceId,@PathVariable Long userId){
		List<TaskUserDTO> taskUserDTOs = taskUserServ.listAllByUserIdAndWorkspaceId(workspaceId, userId);
		return new ResponseEntity<>(taskUserDTOs,HttpStatus.OK);
	}
	@GetMapping("/workspace/first/user/{userId}")
	public ResponseEntity<List<TaskUserDTO>> getAllTaskUserFristWorkspaceAndUserId(@PathVariable Long userId){
		List<TaskUserDTO> taskUserDTOs = taskUserServ.listAllByUserIdAndFirstWorkspace( userId);
		return new ResponseEntity<>(taskUserDTOs,HttpStatus.OK);
	}
	@GetMapping("/statistic")
	public ResponseEntity<Statistic> loadtatisticTask(@RequestParam("userId") Long userId){
		Statistic taskStatistic = taskUserServ.getStatisticByUserId( userId);
		return new ResponseEntity<>(taskStatistic, HttpStatus.OK);

	}

}
