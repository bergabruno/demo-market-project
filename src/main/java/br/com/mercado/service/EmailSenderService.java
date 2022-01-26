package br.com.mercado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public interface EmailSenderService {

    public void sendSimpleEmail(String toEmail, String body, String subject);

    public void sendEmailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException;
    }
