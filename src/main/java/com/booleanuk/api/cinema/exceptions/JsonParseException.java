package com.booleanuk.api.cinema.exceptions;

import com.booleanuk.api.cinema.models.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@Slf4j
@ControllerAdvice
public class JsonParseException {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Response.ErrorMessage>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Error parsing JSON:", ex);
        if (ex.getRootCause() instanceof DateTimeParseException) {
            return ResponseEntity.badRequest().body(Response.error("invalid date format (should be ISO 8601)"));

        }
        return ResponseEntity.internalServerError().body(Response.error("error parsing json"));
    }
}
