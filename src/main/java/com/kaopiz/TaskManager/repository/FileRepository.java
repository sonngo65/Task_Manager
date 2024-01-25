package com.kaopiz.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaopiz.TaskManager.entity.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
