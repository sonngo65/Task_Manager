package com.kaopiz.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.TaskType;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType,Long>{

}
