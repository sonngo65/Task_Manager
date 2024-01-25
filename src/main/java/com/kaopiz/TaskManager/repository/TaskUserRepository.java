package com.kaopiz.TaskManager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.Statistic;
import com.kaopiz.TaskManager.entity.TaskUser;
@Repository
public interface TaskUserRepository extends JpaRepository<TaskUser, Long> {
	@Query(value="SELECT task_user.id,user_id,task_id,workspace_id,status_id,uploaded_file_id,completed_time,progress from task_user INNER JOIN task ON task_user.task_id = task.id where workspace_id=:workspaceId and user_id = :userId  and task.parent_task_id IS NULL",nativeQuery=true )
	List<TaskUser> findAllByWorkspaceIdAndUserId(Long workspaceId,Long userId);
	@Query(value="SELECT task_user.id,user_id,task_id,workspace_id,status_id,uploaded_file_id,completed_time,progress from task_user where task_id = :id",nativeQuery=true )
	List<TaskUser> findAllByTaskId(Long id);
	@Query(value="SELECT task_user.id,user_id,task_id,workspace_id,status_id,uploaded_file_id,completed_time,progress from  task_user left join upload_file on task_user.uploaded_file_id = upload_file.id inner join task on task_user.task_id = task.id where task.parent_task_id = :parentTaskId and user_id = :userId",nativeQuery=true )
	List<TaskUser> findAllByUserIdAndParentTaskId(Long userId,Long parentTaskId);
	@Query(value = "SELECT task_user.id,user_id,task_id,workspace_id,status_id,uploaded_file_id,completed_time,progress from task_user where task_id = :taskId and user_id = :userId", nativeQuery=true)
	Optional<TaskUser> findByUserIdAndTaskId(Long userId,Long taskId);
	
	@Query(value = "SELECT * FROM  task_statistic WHERE  user_id = :userId",nativeQuery = true )
	List<List<Integer>> statisticTask(Long userId);
	
	
	
}
