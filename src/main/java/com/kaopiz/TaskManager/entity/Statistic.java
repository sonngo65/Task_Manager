package com.kaopiz.TaskManager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistic {

	private Long userId;
	private int completedTask;
	private int totalTask;
	private int unfinishedTask;
	
}
