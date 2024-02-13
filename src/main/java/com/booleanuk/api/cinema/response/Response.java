package com.booleanuk.api.cinema.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    protected String status;
    protected T data;

    public Response(T data) {
        this.status = "success";
        this.data = data;
    }

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }

}
