package com.booleanuk.api.cinema.response;

import lombok.Getter;

@Getter
public class Response<T>{
    protected String status;
    protected T data;

    public Response(T success, T movies) {

    }

    public Response() {

    }

    public void set (T data){
        this.status = "success";
        this.data = data;
    }
}