package com.kaopiz.TaskManager.payload;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String userName;
	private Date dateOfBirth;
	private String emailAddress;
}
