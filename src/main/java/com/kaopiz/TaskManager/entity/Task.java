package com.kaopiz.TaskManager.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@OneToMany(mappedBy = "task")
	private Set<TaskUser> taskUsers; 
	
	@ManyToOne
	@JoinColumn(name ="task_type_id")
	private TaskType taskType; 
	
	@ManyToOne
	@JoinColumn(name = "task_info_id")
	private TaskInfo taskInfo;
	
	@ManyToOne
	@JoinColumn(name = "assigner_id")
	private Account assigner;
	
	
	@OneToMany(mappedBy = "task")
	private Set<TaskStatus> taskStatus;
	
	@OneToMany(mappedBy = "task")
	private Set<Worklog> worklogs;
}
