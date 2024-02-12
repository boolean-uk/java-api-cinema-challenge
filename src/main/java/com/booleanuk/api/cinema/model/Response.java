package com.booleanuk.api.cinema.model;

import lombok.Getter;

@Getter
public class Response<T> {
    private String status;
    private T data;

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }
}
