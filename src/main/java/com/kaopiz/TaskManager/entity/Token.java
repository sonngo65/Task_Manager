package com.kaopiz.TaskManager.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor 
@NoArgsConstructor
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String token;
	private String tokenType = "Bearer";
	private boolean revoked;
	private boolean isexpired;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account account;
	
}
