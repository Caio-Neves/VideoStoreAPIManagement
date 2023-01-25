package com.example.videolocadora.services;

import com.example.videolocadora.enums.StatusEmail;
import com.example.videolocadora.model.EmailModel;
import com.example.videolocadora.model.LocadoraModel;
import com.example.videolocadora.repositories.EmailRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    final EmailRepository emailRepository;
    final JavaMailSender emailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }


    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());

        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(emailModel.getEmailTo());
            message.setFrom(emailModel.getEmailFrom());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());

            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);

        }catch(MailException ex){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }finally{
            return emailRepository.save(emailModel);
        }
    }
}
