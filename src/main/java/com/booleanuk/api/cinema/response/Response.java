package com.booleanuk.api.cinema.response;

import lombok.Getter;

@Getter
public class Response<T> {
    protected String status;
    protected T data;

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }
    /*
    Fully aware that im being inconsistent
    when creating separate classes for only the movieResponse and not the others
     */
}
