package com.monocept.insurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.EmailSenderDto;
import com.monocept.insurance.dto.Message;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public Message sendSimpleEmail(EmailSenderDto emailSenderDto) {
    	Message msg = new Message();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSenderDto.getSenderEmail());
        message.setTo(emailSenderDto.getReciverEmail());
        message.setText(emailSenderDto.getBody());
        message.setSubject(emailSenderDto.getSubject());
        mailSender.send(message);
        System.out.println("Mail Send...");
		msg.setStatus(HttpStatus.OK);
		msg.setMessage("Mail sent................");
		return msg;


    }

}
