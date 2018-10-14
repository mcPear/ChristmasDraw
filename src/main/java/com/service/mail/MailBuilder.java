package com.service.mail;

import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import org.springframework.util.StringUtils;

public class MailBuilder {

    private final User user;
    private final User drawUser;
    private final Group group;
    private final String lang;
    private StringBuilder builder;

    MailBuilder(Membership membership) {
        this.user = membership.getUser();
        this.drawUser = membership.getDrawnUser();
        this.group = membership.getGroup();
        this.lang = user.getLang();
        this.builder = new StringBuilder();
    }

    public String getSubject() {
        switch (this.lang) {
            case "pl":
                return "Przeprowadzono losowanie!";
            case "en":
                return "Draw performed!";
            default:
                throw new IllegalArgumentException("Missing mail subject language: " + lang);
        }
    }

    public String getText() {
        return draw().about().includeChildren().goodLuck();
    }

    private MailBuilder draw() {
        String template;
        switch (lang) {
            case "pl":
                template = "Cześć %s,\n" +
                        "właściciel twojej grupy %s przeprowadził losowanie.\n" +
                        "Teraz, przygotuj prezent dla %s %s.\n";
                break;
            case "en":
                template = "Hi %s,\n" +
                        "owner of your group %s has performed a draw.\n" +
                        "Now, prepare a gift for %s %s.\n";
                break;
            default:
                throw new IllegalArgumentException("Missing mail text language: " + lang);
        }
        builder.append(String.format(template, user.getFirstName(), group.getName(), drawUser.getFirstName(), drawUser.getLastName()));
        return this;
    }

    private MailBuilder about() {
        String template;
        if (!StringUtils.isEmpty(drawUser.getAbout())) {
            switch (lang) {
                case "pl":
                    template = "%s o sobie:\n" +
                            "%s\n\n";
                    break;
                case "en":
                    template = "About %s:\n" +
                            "%s\n\n";
                    break;
                default:
                    throw new IllegalArgumentException("Missing mail text language: " + lang);
            }
            builder.append(String.format(template, drawUser.getFirstName(), drawUser.getAbout()));
        }
        return this;
    }

    private MailBuilder includeChildren() {
        String template;
        if (group.isCountChildren()) {
            switch (lang) {
                case "pl":
                    template = "W losowaniu zostały uwzględnione dzieci. Składka na prezent dla dzieci wynosi %s%s.\n" +
                            "Kontakt do osoby zbierającej pieniądze: %s.\n\n";
                    break;
                case "en":
                    template = "Draw includes children. Due for children gifts is %s%s.\n" +
                            "Money collector contact: %s.\n\n";
                    break;
                default:
                    throw new IllegalArgumentException("Missing mail text language: " + lang);
            }
            builder.append(String.format(template, group.getCalculatedChildGiftValue(), " " + group.getCurrency(),
                    group.getCollectorContact()));
        }
        return this;
    }

    private String goodLuck() {
        String template;
        switch (lang) {
            case "pl":
                template = "Powodzenia !";
                break;
            case "en":
                template = "Good luck !";
                break;
            default:
                throw new IllegalArgumentException("Missing mail text language: " + lang);
        }
        builder.append(template);
        return builder.toString();
    }

}
