package com.kaopiz.TaskManager.payload;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDto {
	@NotBlank( message = "First Name Không được để trống")
	private String firstName;
	@NotBlank( message = "Last Name Không được để trống")
	private String lastName;
	@NotBlank( message = "Username Không được để trống")
	private String username;
	
	@NotBlank( message = "Password Không được để trống")
	private String password;
	@NotNull
	private Date dateOfBirth;
	@NotBlank( message = "Email Không được để trống")
	private String emailAddress;	
	private Long roleId;
}
