package com.kaopiz.TaskManager.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kaopiz.TaskManager.entity.File;
import com.kaopiz.TaskManager.entity.UploadFile;
import com.kaopiz.TaskManager.exception.ResourceNotFoundException;
import com.kaopiz.TaskManager.payload.FileRemoveDTO;
import com.kaopiz.TaskManager.repository.FileRepository;
import com.kaopiz.TaskManager.repository.UploadFileRepository;
import com.kaopiz.TaskManager.service.FileStorageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/files")
@CrossOrigin
@AllArgsConstructor

public class FileStorageController {

	private final FileStorageService fileStorageServ;
	private final FileRepository fileRepo;
	private final UploadFileRepository uploadFileRepo;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile[] file) {
		fileStorageServ.uploads(file);
		return new ResponseEntity<>("upload file successfully", HttpStatus.OK);

	}

	@GetMapping("/{fileName}")
	public ResponseEntity<InputStreamResource> getFile(@PathVariable String fileName) throws IOException {
		var fileSource = new ClassPathResource("com/kaopiz/TaskManager/upload/" + fileName);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
				.body(new InputStreamResource(fileSource.getInputStream()));
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<?> getDownLoadFile(@PathVariable String fileName) {
		Resource resource = null;
		try {
			resource = fileStorageServ.download(fileName);
		} catch (Exception e) {
			return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);

		}
		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<FileRemoveDTO> removeFile(@PathVariable Long id) {
		FileRemoveDTO fileRemoveDTO = new FileRemoveDTO();
		File file = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
		fileRepo.deleteById(id);

		UploadFile uploadFile = uploadFileRepo.findById(file.getUploadFile().getId()).get();
		if (uploadFile.getFiles().size() == 0) {
			fileRemoveDTO.setStatus("Delivered");
		}

		fileRemoveDTO.setId(id);
		fileRemoveDTO.setTaskId(file.getUploadFile().getTaskUser().getTask().getId());
		return new ResponseEntity<>(fileRemoveDTO, HttpStatus.OK);
	}
}
