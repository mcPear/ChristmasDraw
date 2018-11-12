package com.service.mail;

import com.domain.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    public MailService(@Autowired JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(List<Membership> memberships) {
        List<SimpleMailMessage> messages = new ArrayList<>();
        memberships.forEach(m -> messages.add(message(m)));
        emailSender.send(messages.toArray(new SimpleMailMessage[messages.size()]));
    }

    private SimpleMailMessage message(Membership membership) {
        MailBuilder mailBuilder = new MailBuilder(membership);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(membership.getUser().getEmail());
        message.setSubject(mailBuilder.getSubject());
        message.setText(mailBuilder.getText());
        return message;
    }

}
