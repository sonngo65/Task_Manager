package com.kaopiz.TaskManager.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OrderBy    
	private String content;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@OneToMany(mappedBy="task",cascade = CascadeType.ALL)
	private List<File> uploadedFiles = new ArrayList<File>();

	
	@OneToMany(mappedBy = "task",cascade = CascadeType.ALL)
	private Set<TaskUser> taskUsers; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="task_type_id")
	private TaskType taskType; 
	
	
	@ManyToOne
	@JoinColumn(name = "assigner_id")
	private Account assigner;
	
	@ManyToOne
	@JoinColumn(name = "parent_task_id")
	private Task parentTask;
	
	@OneToMany(mappedBy="parentTask",cascade = CascadeType.ALL)
	private Set<Task> childTasks;
	
	
	@OneToMany(mappedBy = "task")
	private Set<Worklog> worklogs;

	

	
	
	
	
}
