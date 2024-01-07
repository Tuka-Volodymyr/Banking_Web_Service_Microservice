package com.bank.mailservice.service.serviceImpl;

import com.bank.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendCodeToEmailForChangePassword(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please confirm your email address");
        message.setText("""
            Password for change code:\s
            """ + code);
        javaMailSender.send(message);
    }
}
