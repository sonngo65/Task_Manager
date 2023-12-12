package com.kaopiz.TaskManager.payload;

import java.util.Date;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

	private String status;
	private String message;
	private String token;
	private Date expiresIn;
	
}
