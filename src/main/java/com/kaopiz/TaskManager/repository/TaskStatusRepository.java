package com.kaopiz.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.TaskStatus;

@Repository 
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long>{

}
