package com.booleanuk.api.cinema.responses;

import com.booleanuk.api.cinema.models.Customer;
import lombok.Getter;

@Getter
public abstract class Response <T>{
    protected String status;
    protected T data;

    public void set(T data){
        this.status = "success";
        this.data = data;
    }

}
