package com.kaopiz.TaskManager.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenTaskUserDTO {
	private Long id;
	private AccountDTO user;
	private List<TaskDTO> childrenTask;
	
}

