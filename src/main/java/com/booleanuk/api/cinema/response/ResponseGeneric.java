package com.booleanuk.api.cinema.response;

import lombok.Getter;

@Getter
public class ResponseGeneric<T>{
    protected String status;
    protected T data;

    public void set (T data){
        this.status = "success";
        this.data = data;
    }
}
