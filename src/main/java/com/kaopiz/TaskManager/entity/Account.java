package com.kaopiz.TaskManager.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	private String emailAddress;
	
	@OneToMany(mappedBy = "account")
	private Set<TaskUser> taskUsers;
	
	@OneToMany(mappedBy ="assigner")
	private Set<Task> task;
	
	@OneToMany(mappedBy = "account")
	private Set<Worklog> worklogs;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
}
