package com.kaopiz.TaskManager.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String content;
	private String description;
	private Long estimated_time;
	@Temporal(TemporalType.TIMESTAMP)
	private Date start_time;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date estimate;
	
	@OneToMany(mappedBy= "taskInfo")
	private Set<Task> tasks;
	
	
}
