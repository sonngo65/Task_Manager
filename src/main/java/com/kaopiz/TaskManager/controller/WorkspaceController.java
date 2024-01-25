package com.kaopiz.TaskManager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.payload.RoleDTO;
import com.kaopiz.TaskManager.payload.WorkspaceCreateDTO;
import com.kaopiz.TaskManager.payload.WorkspaceDTO;
import com.kaopiz.TaskManager.service.WorkspaceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/workspace")
@CrossOrigin
@AllArgsConstructor
public class WorkspaceController {
	private final WorkspaceService workspaceServ;
	
	
	@GetMapping
	public ResponseEntity<WorkspaceDTO> getworkspaceById(@RequestParam("workspaceId") Long workspaceId,@RequestParam("userId") Long userId){
		WorkspaceDTO workspaceResponse = workspaceServ.findByWorkspaceIdAndUserId(workspaceId,userId);
		return new ResponseEntity<>(workspaceResponse,HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<WorkspaceDTO> saveworkspace(@RequestBody WorkspaceCreateDTO workspaceRequest){
		WorkspaceDTO workspaceResponse = workspaceServ.save(workspaceRequest);
		return new ResponseEntity<>(workspaceResponse,HttpStatus.OK);
	}
	@PutMapping
	public ResponseEntity<WorkspaceDTO> addUserIntoworkspace(@RequestParam("code") String code ,@RequestParam("userId")  Long userId){
		WorkspaceDTO workspaceResponse = workspaceServ.addUser(code, userId);
		return new ResponseEntity<>(workspaceResponse,HttpStatus.OK);
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<List<WorkspaceDTO>> listAllworkspaceByUserId(@PathVariable Long id){
		List<WorkspaceDTO> workspacesResponse = workspaceServ.findAllByUserId(id);
		return new ResponseEntity<>(workspacesResponse,HttpStatus.OK);
	}
	@GetMapping("/user/{id}/first")
	public ResponseEntity<WorkspaceDTO> getFirstworkspace(@PathVariable Long id){
		WorkspaceDTO workspaceResponse = workspaceServ.getFirst(id);
		return new ResponseEntity<>(workspaceResponse,HttpStatus.OK);
	}
	@GetMapping("/first/user/{userId}/role")
	public ResponseEntity<RoleDTO> getRoleNameByfirstAndUserId(@PathVariable Long userId){
		RoleDTO role = workspaceServ.getRoleByFirstAndUserId(userId);
		return new ResponseEntity<>(role,HttpStatus.OK);
	}
	@GetMapping("/{workspaceId}/user/{userId}/role")
	public ResponseEntity<RoleDTO> getRoleNameByWorkspaceIdAndUserId(@PathVariable Long workspaceId,@PathVariable Long userId){
		RoleDTO role = workspaceServ.getRoleByWorkspaceIdAndUserId(workspaceId,userId);
		return new ResponseEntity<>(role,HttpStatus.OK);
	}
}
