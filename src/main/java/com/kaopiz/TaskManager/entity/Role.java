package com.kaopiz.TaskManager.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	 
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission",
				joinColumns = @JoinColumn(name = "role_id"),
				inverseJoinColumns = @JoinColumn(name = "permission_id")
			)
	
	private Set<Permission> permissions;
	
	
	@OneToMany(mappedBy = "role",cascade =  CascadeType.ALL)
	private Set<Account> accounts;
	
	@OneToMany(mappedBy="role")
	private Set<WorkspaceUser> workspaceUsers;

	
	
}
