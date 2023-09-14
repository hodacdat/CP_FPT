//package com.example.demo.mailHelper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/apisend")
//public class MainController {
//
//	@Autowired
//	private MailService mailService;
//
//	@PostMapping("/send-mail")
//	public String sendMail(@RequestBody MailDetail mailDetail) {
//		return mailService.sendMail(mailDetail);
//	}
//
//	// Sending email with attachment
//	@PostMapping("/send-mail-attachment")
//	public String sendMailWithAttachment(@RequestBody MailDetail mailDetail) {
//		return mailService.sendMailWithAttachment(mailDetail);
//	}
//
//}
