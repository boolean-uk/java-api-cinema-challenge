package com.booleanuk.api.cinema.exceptionHandlers;

import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e){
        ErrorResponse error = new ErrorResponse();
        error.set("bad request");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
