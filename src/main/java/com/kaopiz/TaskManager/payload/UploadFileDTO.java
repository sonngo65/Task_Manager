package com.kaopiz.TaskManager.payload;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDTO {
	private Long id;
	private Long taskId;
	private String status;
	private Date uploadedTime;
	private List<FileDTO> files;
}
