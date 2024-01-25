package com.kaopiz.TaskManager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaopiz.TaskManager.entity.UploadFile;
import com.kaopiz.TaskManager.payload.TaskCreateDTO;
import com.kaopiz.TaskManager.payload.TaskDTO;
import com.kaopiz.TaskManager.payload.UploadFileDTO;
import com.kaopiz.TaskManager.service.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin
@AllArgsConstructor
@SecurityRequirement(name = "Authorization Bearer")
public class TaskController {
	
	private final TaskService  taskService;
	@GetMapping
	public List<TaskDTO> getAllTask(){
		return taskService.listAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<TaskCreateDTO> getTaskByID(@PathVariable Long id){
			TaskCreateDTO taskResponse = taskService.findById(id);
			return new ResponseEntity<>(taskResponse,HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<TaskDTO>  postTask(@RequestBody TaskCreateDTO taskRequest) {
		TaskDTO taskResponse = taskService.save(taskRequest);		
		return new ResponseEntity<>(taskResponse,HttpStatus.OK);
	}
	@GetMapping("/parent-task")
	public ResponseEntity<List<TaskDTO>> getTasksByParentId(@RequestParam("userId") Long userId,@RequestParam("parentTaskId") Long parentTaskId){
		List<TaskDTO> tasksResponse = taskService.findAllByUserIdAndParentTaskId(userId,parentTaskId);
		return new ResponseEntity<>(tasksResponse,HttpStatus.OK);
	}
	@PutMapping("/completed")
	public ResponseEntity<TaskDTO> completedChildrenTask(@RequestParam("childTaskId") Long childTaskId,@RequestParam("userId") Long userId,@RequestParam("statusId") Long statusId){
		TaskDTO taskResponse = taskService.updateTask(childTaskId,userId,statusId);
		return new ResponseEntity<>(taskResponse,HttpStatus.OK);
	}					
	@GetMapping("/status")
	public ResponseEntity<TaskDTO> getTaskStatus(@RequestParam("taskId") Long taskId,@RequestParam("userId") Long userId){
		TaskDTO taskResponse = taskService.getStatusTask(taskId,userId);
		return new ResponseEntity<>(taskResponse,HttpStatus.OK);

	}
	@PutMapping("/upload-file")
	public ResponseEntity<UploadFileDTO> uploadFile(@RequestBody UploadFile uploadFile,@RequestParam("userId") Long userId,@RequestParam("taskId") Long taskId){
		UploadFileDTO uploadFileResponse = taskService.uploadFile(uploadFile, userId, taskId);
		return new ResponseEntity<>(uploadFileResponse,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskDTO> updateTaskById(@RequestBody TaskDTO taskRequest,@PathVariable Long id){
		TaskDTO taskResponse = taskService.updateById(id,taskRequest);
		return new ResponseEntity<>(taskResponse,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<TaskDTO> deleteTaskById(@PathVariable Long id){
		TaskDTO taskResponse = taskService.deleteById(id);
		return new ResponseEntity<>(taskResponse,HttpStatus.OK);
	}
}
