package com.kaopiz.TaskManager.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

	private Long accountId;
	private String password;
}
