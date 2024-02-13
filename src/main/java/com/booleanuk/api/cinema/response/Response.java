package com.booleanuk.api.cinema.response;

import lombok.Getter;

@Getter
public class Response {
    protected String status;
    protected Object data;

    public void set(Object data) {
        this.status = "success";
        this.data = data;
    }

}
