package com.emretaskin.definexFinalCase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IdNumberAlreadyInUseException extends RuntimeException{
    public IdNumberAlreadyInUseException(String message){
        super(message);
    }
}
