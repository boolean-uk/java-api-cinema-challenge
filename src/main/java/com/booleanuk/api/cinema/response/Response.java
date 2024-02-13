package com.booleanuk.api.cinema.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private String status;
    private T data;

    public Response(String status, T data) {
        this.status = status;
        this.data = data;
    }
}
