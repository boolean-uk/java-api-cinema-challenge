package com.booleanuk.api.cinema.responses;

import lombok.Getter;

@Getter
public class Response<T> {
    protected String status;
    protected T data;

    public Response(T data) {
        this.status = "Success";
        this.data = data;
    }

}
