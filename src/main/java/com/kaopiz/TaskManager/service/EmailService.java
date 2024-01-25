package com.kaopiz.TaskManager.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kaopiz.TaskManager.payload.ConfirmationCode;
import com.kaopiz.TaskManager.payload.ConfirmationCodeRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
	private final JavaMailSender javaMailSender;
	public Map<Long, ConfirmationCode> confirmationCodes = new HashMap<Long, ConfirmationCode>();

	public void sendConfirmationEmail(Long accountId, String toEmail) {
		String confirmationCode = generationCode();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("ssngotat@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("Quên mật khẩu ! ");
		mailMessage.setText("Mã xác nhân: " + confirmationCode);
		confirmationCodes.put(accountId,
				new ConfirmationCode(confirmationCode, new Date(System.currentTimeMillis() + 60 * 100)));

		javaMailSender.send(mailMessage);
	}

	public Boolean confirmCode(ConfirmationCodeRequest confirmationCodeRequest) {
		if (confirmationCodes.get(confirmationCodeRequest.getAccountId()).getCode().equals(confirmationCodeRequest.getCode())) {
			return true;
		}

		return false;
	}

	public String generationCode() {
		Random random = new Random();
		int confirmationCode = 1000000 + random.nextInt(999999);
		return String.valueOf(confirmationCode).substring(1);
	}
}
