package com.kaopiz.TaskManager.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
	
	private Long id;
	private Long assignerId;
	private Long taskTypeId;
	private Long parentTaskId;
	private TaskInfoDto taskInfo;
}
