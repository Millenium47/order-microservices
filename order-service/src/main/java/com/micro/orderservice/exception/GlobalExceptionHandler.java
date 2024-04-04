package com.micro.orderservice.exception;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<?> handleOutOfStockException(OutOfStockException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage(), "outOfStockItems", e.getOutOfStockItems()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}