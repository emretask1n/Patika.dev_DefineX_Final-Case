package com.emretaskin.definexFinalCase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoanFormInquiryException extends RuntimeException {
    public InvalidLoanFormInquiryException(String message) {
        super(message);
    }
}
