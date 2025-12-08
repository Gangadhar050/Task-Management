package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.DTO.EmailLogDTO;
import com.TaskManagement.Entity.EmailLog;
import com.TaskManagement.Repository.EmailLoginRepository;
import com.TaskManagement.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {


    @Autowired
     private EmailLoginRepository emailLoginRepository;

    @Autowired
     private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(EmailLogDTO emailLog) {
        boolean sentStatus = false;
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailLog.getRecipientEmail());
            helper.setSubject(emailLog.getSubject());
            helper.setText(emailLog.getBody(), true);
            javaMailSender.send(message);
            sentStatus = true;
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();

        }
        EmailLog log = new EmailLog();
        emailLoginRepository.save(log);
        return sentStatus ? "Email sent successfully" : "Failed to send email";
    }
}
   // Implementing Email Sender manually
//    @Override
//    public void sendEmail(EmailLog emailLog) {
//        final String fromEmail ="gangadharhosamani050@gmail.com";
//        final String password="QWERTYasdf@050";
//
//        Properties props= new Properties();
//            props.put("mail.smpt.host","smpt.gmail.com")   ;
//            props.put("mail,smpt.port","587");//default port
//            props.put("mail,smpt.auth","true");
//            props.put("mail,smpt.starttls.enable","true");
//
//        Session session= Session.getInstance(props,new javax.mail.Authenticator(){
//            protected  PasswordAuthentication getPasswordAuthenticaton() {
//                return new PasswordAuthentication(fromEmail, password);
//            }
//        });
//        try {
//            Message msg= new MimeMessage(session);
//            msg.setFrom(new InternetAddress(fromEmail));
//            msg.setRecipient(Message.RecipientType.TO,InternetAddress.parse(emailLog.getRecipientEmail())[0]);
//            msg.setSubject(emailLog.getSubject());
//            msg.setText(emailLog.getBody());
//
//            Transport.send(msg);
//            System.out.println("Email Sent to "+emailLog.getRecipientEmail());
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to send Email"+e);
//        }
//    }
//}


//   Another way to configure JavaMailSender bean

// @Bean
//     public JavaMailSender javaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("your_email@gmail.com");
//        mailSender.setPassword("your_app_password");
//
//Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;