package com.intuitcraft.onboarding.exceptionHandler;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class IdentityException extends RuntimeException{

    private String code;
    private String message;

    private HttpStatus status;

    public IdentityException(String code, String message, HttpStatus status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
