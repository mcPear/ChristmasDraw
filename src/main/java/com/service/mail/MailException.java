package com.service.mail;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Sending mail problem")
public class MailException extends RuntimeException {

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
