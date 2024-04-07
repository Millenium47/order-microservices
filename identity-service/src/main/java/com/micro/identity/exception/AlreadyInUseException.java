package com.micro.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyInUseException extends RuntimeException {
    public  AlreadyInUseException() {super();}

    public AlreadyInUseException(String message) {
        super(message);
    }
}
