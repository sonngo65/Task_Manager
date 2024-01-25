package com.kaopiz.TaskManager.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
 
public class AccountDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private Date dateOfBirth;
	private String emailAddress;	
	private String roleName;
}
