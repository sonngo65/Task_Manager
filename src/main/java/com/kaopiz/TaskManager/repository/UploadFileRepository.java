package com.kaopiz.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaopiz.TaskManager.entity.UploadFile;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

}
