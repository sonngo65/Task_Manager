package com.kaopiz.TaskManager.service;

import java.util.List;

import com.kaopiz.TaskManager.entity.UploadFile;
import com.kaopiz.TaskManager.payload.TaskCreateDTO;
import com.kaopiz.TaskManager.payload.TaskDTO;
import com.kaopiz.TaskManager.payload.UploadFileDTO;

public interface TaskService {
	List<TaskDTO> listAll();
	TaskCreateDTO findById(Long id);
	TaskDTO save(TaskCreateDTO taskRequest);
	List<TaskDTO> findAllByUserIdAndParentTaskId(Long userId,Long parentTaskId);
	TaskDTO updateTask(Long childTaskId,Long userId,Long statusId);
	UploadFileDTO uploadFile(UploadFile uploadFile,Long userId,Long taskId);
	TaskDTO getStatusTask(Long taskId,Long userId);
	TaskDTO updateById(Long id,TaskDTO taskRequest);
	TaskDTO deleteById(Long id);
}
