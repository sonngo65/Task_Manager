package com.kaopiz.TaskManager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	private int errorCode;
	private String devMsg;
	private String userMsg;
}
