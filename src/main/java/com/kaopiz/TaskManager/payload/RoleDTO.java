package com.kaopiz.TaskManager.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	private Long id;
	private String name;
	private List<PermissionDTO> permissionDTOs; 
}
