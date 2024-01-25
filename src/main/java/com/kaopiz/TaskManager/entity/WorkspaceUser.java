package com.kaopiz.TaskManager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkspaceUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "workspace_id")
	private Workspace workspace; 
	@ManyToOne
	@JoinColumn(name= "role_id")
	private Role role;
	
}
