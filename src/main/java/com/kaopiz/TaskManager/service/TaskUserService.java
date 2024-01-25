package com.kaopiz.TaskManager.service;

import java.util.List;

import com.kaopiz.TaskManager.entity.Statistic;
import com.kaopiz.TaskManager.payload.ChildrenTaskUserDTO;
import com.kaopiz.TaskManager.payload.ParentTaskUserDTO;
import com.kaopiz.TaskManager.payload.TaskUserDTO;

public interface TaskUserService {
	 ChildrenTaskUserDTO findByTaskId(Long parentId,Long userId);
	 List<ParentTaskUserDTO> findById(Long taskId);
	 List<TaskUserDTO> listAllByUserIdAndWorkspaceId(Long workspaceId,Long userId);
	 List<TaskUserDTO> listAllByUserIdAndFirstWorkspace(Long userId);
	 Statistic getStatisticByUserId(Long userId);

}
