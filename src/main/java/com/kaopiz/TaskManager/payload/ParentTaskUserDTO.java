package com.kaopiz.TaskManager.payload;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.kaopiz.TaskManager.entity.Account;
import com.kaopiz.TaskManager.entity.Status;
import com.kaopiz.TaskManager.entity.Task;
import com.kaopiz.TaskManager.entity.UploadFile;
import com.kaopiz.TaskManager.entity.Workspace;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentTaskUserDTO {
	private Long id;	
	private Long userId;
	private String content;
	private String firstName;
	private String lastName;
	private int progress;
	private Date completedTime;
}
