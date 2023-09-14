package com.example.demo.mailHelper;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendMail(MailDetail mailDetail) {
		try {
			// Creating a simple mail message object
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);

			String email = mailDetail.getRecipient();
			String username = email.substring(0, email.indexOf('@'));

			System.out.println("Username: " + username);

			String template = getEmailTemplateResetPass(username, mailDetail.getMsgBody());

			// Setting up necessary details of mail
			String from = sender;
			String to = mailDetail.getRecipient();
			String subject = mailDetail.getSubject();

			mailMessage.setFrom(sender);
			mailMessage.setTo(mailDetail.getRecipient());
			mailMessage.setSubject(mailDetail.getSubject());
			mailMessage.setText(template, true);

			// Sending the email
			mailSender.send(message);
			return "sent success";

		} catch (Exception e) {
			// TODO: handle exception
			return "Error while Sending email!!!";
		}
	}

	@Override
	public String sendMailWithAttachment(MailDetail mailDetail) {
		// Creating a Mime Message can send attachment, special character encoding....
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {
			// Setting multipart as true for attachment to be send
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(mailDetail.getRecipient());
			mimeMessageHelper.setSubject(mailDetail.getSubject());
			mimeMessageHelper.setText(mailDetail.getMsgBody());

			// add file attachment
			FileSystemResource file = new FileSystemResource(new File(mailDetail.getAttachment()));

			mimeMessageHelper.addAttachment(file.getFilename(), file);

			// sending
			mailSender.send(mimeMessage);
			return "sent success";
		} catch (Exception e) {
			// TODO: handle exception
			return "Error while Sending email!!!";
		}
	}

	private static String getEmailTemplateResetPass(String recipientName, String otp) {
		String template = "<html><body>";
		template += "<h3>Dear " + recipientName + ", </h3>";
		template += "<p>You've requested an OTP for your Clinicmate account. Please use the OTP below :</p>";
		template += "<p><strong>OTP: " + otp + "</strong></p>";
		template += "<p>Please use this OTP before 2 minutes. After that, OTP is not valid.</p>";
		template += "<p>Best regards,</p>";
		template += "<p>The Clinicmate Team</p>";
		template += "</body></html>";

		return template;
	}

}
