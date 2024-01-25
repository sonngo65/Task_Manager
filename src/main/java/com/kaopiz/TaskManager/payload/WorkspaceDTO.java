package com.kaopiz.TaskManager.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDTO {
	private Long id;
	private String name;
	private String code;
	private String roleName;
	private List<TaskDTO> tasks;
	private List<AccountDTO> users;
}
