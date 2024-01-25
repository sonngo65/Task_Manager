package com.kaopiz.TaskManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.TaskUser;
import com.kaopiz.TaskManager.entity.WorkspaceUser;
@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long>{
	@Query(value="SELECT workspace_user.id,user_id,workspace_id,role_id from workspace_user where user_id = :userId And workspace_id = :workspaceId",nativeQuery=true )
	WorkspaceUser findByWorkspaceIdAndUserId(Long workspaceId,Long userId);
}
