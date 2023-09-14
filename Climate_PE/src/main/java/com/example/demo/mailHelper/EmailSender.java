//package com.example.demo.mailHelper;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//public class EmailSender {
//
//    private JavaMailSender javaMailSender;
//    
//    public EmailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//    
//    public void sendEmail(String recipient, String subject, String template) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        
//        helper.setTo(recipient);
//        helper.setSubject(subject);
//        helper.setText(template, true);
//        
//        javaMailSender.send(message);
//    }
//    
//    public static void main(String[] args) throws MessagingException {
//        JavaMailSender javaMailSender = getJavaMailSender();
//        EmailSender emailSender = new EmailSender(javaMailSender);
//        
//        String recipient = "nguyen@example.com";
//        String subject = "Reset Your Clinicmate Password - OTP Verification";
//        String template = getEmailTemplate("Nguyen Quang Hung", "09xl43se");
//        
//        emailSender.sendEmail(recipient, subject, template);
//    }
//    
//    private static JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("your-mail-server-host");
//        mailSender.setPort(587);
//        mailSender.setUsername("your-username");
//        mailSender.setPassword("your-password");
//        
//        // Additional mail server configuration, if needed
//        
//        return mailSender;
//    }
//    
//    private static String getEmailTemplate(String recipientName, String otp) {
//        String template = "<html><body>";
//        template += "<h2>Subject: Reset Your Clinicmate Password - OTP Verification</h2>";
//        template += "<p>Dear " + recipientName + ",</p>";
//        template += "<p>You've requested a password reset for your Clinicmate account. Please use the OTP below to verify your identity and set a new password:</p>";
//        template += "<p><strong>OTP: " + otp + "</strong></p>";
//        template += "<p>Best regards,</p>";
//        template += "<p>The Clinicmate Team</p>";
//        template += "</body></html>";
//        
//        return template;
//    }
//}