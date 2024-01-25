package com.kaopiz.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
	
}
