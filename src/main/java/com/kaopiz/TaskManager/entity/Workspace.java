package com.kaopiz.TaskManager.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Workspace {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	@OrderBy
	private String name;
	
	@OneToMany(mappedBy = "workspace",cascade = CascadeType.ALL)
	private Set<WorkspaceUser> workspaceUsers;
	
	@OneToMany(mappedBy = "workspace")
	private Set<TaskUser> taskUser;
	
	private String code;
}
