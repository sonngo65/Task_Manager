package com.kaopiz.TaskManager.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaopiz.TaskManager.entity.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDTO {
	
	@JsonIgnore
	private Long Id;
	private String content;
	private String description;
	private Date createdTime;	
	private Date startTime;
	private Date endTime;
	private List<FileDTO> uploadedFiles = new ArrayList<FileDTO>();
	private List<AccountDTO> members;
	private Long assignerId;
	private Long taskTypeId;
	private Long parentTaskId;
	private Long workspaceId;
}
