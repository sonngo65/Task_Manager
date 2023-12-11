package com.kaopiz.TaskManager.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Worklog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startedWorkAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endedWorkAt;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
}
