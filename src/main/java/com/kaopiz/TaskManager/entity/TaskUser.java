package com.kaopiz.TaskManager.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task; 
	@ManyToOne
	@JoinColumn(name = "workspace_id")
	private Workspace workspace;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "uploaded_file_id",referencedColumnName = "id")
	private UploadFile uploadedFile;
	
	
	private Date completedTime;
	
	@Min(value = 0)
    @Max(value = 100)
	private int progress;
	 
}
