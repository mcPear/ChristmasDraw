package com.service;

import com.domain.User;
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

    public void send(User user, User drawUser, String groupName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(getSubject(user.getLang()));
        message.setText(getText(user, drawUser, groupName));
        emailSender.send(message);
    }

    private String getSubject(String lang) {
        switch (lang) {
            case "pl":
                return "Przeprowadzono losowanie!";
            case "en":
                return "Draw performed!";
            default:
                throw new IllegalArgumentException("Missing mail subject language: " + lang);
        }
    }

    private String getText(User user, User drawUser, String groupName) {
        String template;
        String lang = user.getLang();
        switch (lang) {
            case "pl":
                template = getPolishTemplate();
                break;
            case "en":
                template = getEnglishTemplate();
                break;
            default:
                throw new IllegalArgumentException("Missing mail text language: " + lang);
        }
        return String.format(template,
                user.getFirstName(), groupName, drawUser.getFirstName(), drawUser.getLastName(), drawUser.getFirstName(),
                drawUser.getAbout()); //todo handle 'about' null
    }

    //todo value, include children, children due, contact
    private String getPolishTemplate() {
        return "Cześć %s,\n" +
                "właściciel twojej grupy %s przeprowadził losowanie.\n" +
                "Teraz, przygotuj prezent dla %s %s.\n" +
                "%s o sobie:\n" +
                "%s\n\n" +
                "Powodzenia !";
    }

    private String getEnglishTemplate() {
        return "Hi %s,\n" +
                "owner of your group %s has performed a draw.\n" +
                "Now, prepare a gift for %s %s.\n" +
                "About %s:\n" +
                "%s\n\n" +
                "Good luck !";
    }

}
