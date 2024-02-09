package com.booleanuk.api.cinema.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleCustomApiException(ApiException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(new ErrorResponseMessage(ex.getMessage()));
        return new ResponseEntity<>(apiErrorResponse, ex.getHttpStatus());
    }
}
