package com.kaopiz.TaskManager.payload;

import org.hibernate.annotations.Parent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUserDTO {
	ParentTaskUserDTO parentTask;
	ChildrenTaskUserDTO childTask;
}
