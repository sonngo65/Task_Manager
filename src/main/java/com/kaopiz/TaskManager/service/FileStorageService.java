package com.kaopiz.TaskManager.service;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	void upload(MultipartFile file);
	void uploads(MultipartFile[] file);
	void remove(String fileName);
	Resource download(String fileName)  throws MalformedURLException;
}
