package com.kaopiz.TaskManager.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadFile {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadedTime;
	
	@OneToMany(mappedBy = "uploadFile",cascade = CascadeType.ALL)
	private Set<File> files;
	@OneToOne(mappedBy = "uploadedFile")
	private TaskUser taskUser;
	
}
