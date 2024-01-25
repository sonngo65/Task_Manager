package com.kaopiz.TaskManager.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaopiz.TaskManager.service.FileStorageService;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{
	private final Path uploadPath;
	
	
	@Override
	public void upload(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		try(InputStream inputStream = file.getInputStream()){
			Files.copy(inputStream, this.uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			
		}catch(Exception ex) {
			throw new RuntimeException("Could not saved file : " + ex.getMessage());
		}
		
	}


	@Override
	public void uploads(MultipartFile[] file) {
	 List<MultipartFile> files=	Collections.arrayToList(file);
	 files.stream().forEach((multipartFile)-> upload(multipartFile));
	}


	@Override
	public void remove(String fileName) {
		
	}


	@Override
	public Resource download(String fileName) throws MalformedURLException  {
		Path downloadPath = uploadPath.resolve(fileName);
		Resource fileResource = new UrlResource(downloadPath.toUri());
		return fileResource;
		
	}
	
		
	
}
