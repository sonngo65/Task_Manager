package com.kaopiz.TaskManager.payload;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRemoveDTO {
	private Long id;
	private Long taskId;
	private String status;
}
