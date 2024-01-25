package com.kaopiz.TaskManager.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message ="username Không được để trống")
	private String username;
	@NotBlank(message ="password Không được để trống")
	private String password;
}
