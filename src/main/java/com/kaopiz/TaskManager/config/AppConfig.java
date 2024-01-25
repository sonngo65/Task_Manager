package com.kaopiz.TaskManager.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class AppConfig {
	
	@Bean
	public Path getPath() {
		return Paths.get("src/main/java/com/kaopiz/TaskManager/upload");
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
