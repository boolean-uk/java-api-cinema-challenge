package com.booleanuk.api.cinema.exception;

import com.booleanuk.api.cinema.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleBadRequestExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errorData = new HashMap<>();
        errorData.put("message", "bad request");

        ApiResponse<Map<String, String>> errorResponse = new ApiResponse<>("error", errorData);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleNotFoundException(NotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getLocalizedMessage());

        ApiResponse<Map<String, String>> errorResponse = new ApiResponse<>("error", error);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}


