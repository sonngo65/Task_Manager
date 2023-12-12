package com.kaopiz.TaskManager.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account implements UserDetails{
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
	
	@OneToMany(mappedBy = "account")
	private Set<Token> tokens;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.role.getPermissions().stream().map((permission) ->  new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
