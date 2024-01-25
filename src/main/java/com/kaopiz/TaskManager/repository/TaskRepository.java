package com.kaopiz.TaskManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	@Query(name = "select * from task where parent_task_id = :id",nativeQuery = true)
	List<Task> findByParentTaskId(Long id);
}
