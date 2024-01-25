package com.kaopiz.TaskManager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kaopiz.TaskManager.entity.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long>{
	@Query(value = "select workspace.id,name,code  from workspace "
			+ "left join workspace_user on workspace.id = workspace_user.workspace_id "
			+ "left join account on workspace_user.user_id = account.id"
			+ " where account.id= :id",nativeQuery = true)
	List<Workspace> findAllByUserId(Long id);
	Optional<Workspace> findByCode(String code);
	
}
