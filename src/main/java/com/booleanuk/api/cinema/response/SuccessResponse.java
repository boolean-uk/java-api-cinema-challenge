package com.booleanuk.api.cinema.response;

public class SuccessResponse<T> extends Response<T>{

    public SuccessResponse(T data) {
        super("success", data);
    }
}
