package com.emretaskin.definexFinalCase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserIdOrPasswordIncorrectException extends RuntimeException{
    public UserIdOrPasswordIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
