package com.kaopiz.TaskManager.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class File {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	private String name;
	private String type;
	@ManyToOne
	@JoinColumn(name ="upload_file_id")
	private UploadFile uploadFile;
	@ManyToOne
	@JoinColumn(name ="task_id")
	private Task task;
	
	
}
