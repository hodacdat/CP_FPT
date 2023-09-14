package com.example.demo.mailHelper;

public interface MailService {

	String sendMail(MailDetail mailDetail);

	String sendMailWithAttachment(MailDetail mailDetail);
}
