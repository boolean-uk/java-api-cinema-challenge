package com.booleanuk.api.cinema.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    // Static method to create an HttpStatus OK response
    public static <T> ResponseEntity<ResponseInterface> OkSuccessResponse(T data) {
        SuccessResponse<T> successResponse = new SuccessResponse<>(data);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // Static method to create an HttpStatus CREATED response
    public static <T> ResponseEntity<ResponseInterface> CreatedSuccessResponse(T data) {
        SuccessResponse<T> successResponse = new SuccessResponse<>(data);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    // Static method to create a Bad Request response
    public static ResponseEntity<ResponseInterface> BadRequestErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse("bad request");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Static method to create a Not Found response
    public static ResponseEntity<ResponseInterface> NotFoundErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse("not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
