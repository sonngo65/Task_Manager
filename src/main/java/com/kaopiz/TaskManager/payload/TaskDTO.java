package com.kaopiz.TaskManager.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.kaopiz.TaskManager.entity.File;
import com.kaopiz.TaskManager.entity.UploadFile;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
	
	private Long id;
	private String content;
	private String description;
	private Date createdTime;	
	private Date startTime;
	private Date endTime;
	private List<FileDTO> assignerUploadedFiles = new ArrayList<FileDTO>();
	private Long assignerId;
	private Long taskTypeId;
	private Long parentTaskId;
	private UploadFileDTO memberUploadedFile;
	private String status;
	private int progress;
	
}
