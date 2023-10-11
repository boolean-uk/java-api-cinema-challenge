package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = {IllegalAccessException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleBadRequest() {
        String message = "Could not proceed with that action, please check all fields are correct";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>("Internal Server Error: " + message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
