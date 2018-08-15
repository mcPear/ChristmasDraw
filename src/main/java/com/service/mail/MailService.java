package com.service.mail;

import com.domain.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    public MailService(@Autowired JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(Membership membership) {
        MailBuilder mailBuilder = new MailBuilder(membership);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(membership.getUser().getEmail());
        message.setSubject(mailBuilder.getSubject());
        message.setText(mailBuilder.getText());
        emailSender.send(message);
    }

}
