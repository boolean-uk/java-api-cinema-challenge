package com.booleanuk.api.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private String status;
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<>("success", data);
    }

    public static Response<ErrorMessage> error(String message) {
        return new Response<>("error", new ErrorMessage(message));
    }

    public static final Response<ErrorMessage> BAD_REQUEST = new Response<>("error", new ErrorMessage("bad request"));

    public static final Response<ErrorMessage> NOT_FOUND = new Response<>("error", new ErrorMessage("not found"));

    @Data
    @AllArgsConstructor
    public static class ErrorMessage {
        String message;
    }
}