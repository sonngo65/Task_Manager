package com.kaopiz.TaskManager.payload;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationCode {
	private String code;
	@Temporal(TemporalType.TIME)
	private Date expiration;
}
