package com.booleanuk.api.cinema.util;


import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomErrorException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;





@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(CustomDataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(CustomParameterConstraintException.class)
    public ResponseEntity<ErrorResponse> handleCustomParameterConstraintExceptions(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorExceptions(Exception e) {
        // casting the generic Exception e to CustomErrorException
        CustomErrorException customErrorException = (CustomErrorException) e;

        HttpStatus status = customErrorException.getStatus();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        customErrorException.getMessage()
                ),
                status
        );
    }

    // fallback method
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleExceptions(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }
}