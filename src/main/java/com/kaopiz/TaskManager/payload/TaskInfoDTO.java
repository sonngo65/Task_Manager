package com.kaopiz.TaskManager.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfoDTO {
	private Long id;
	private String content;
	private String description;
	private Date startTime;
	private Date endTime;
	private List<String> uploadedFiles= new ArrayList<String>();

	
}
