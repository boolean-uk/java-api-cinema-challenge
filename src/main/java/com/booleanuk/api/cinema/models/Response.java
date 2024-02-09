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

    public static Response<String> error(String message) {
        return new Response<>("error", message);
    }

    public static final Response<String> BAD_REQUEST = new Response<>("error", "bad request");

    public static final Response<String> NOT_FOUND = new Response<>("error", "not found");
}
