package com.booleanuk.api.cinema.response;

public class SuccessResponse extends Response{

    public SuccessResponse(Object data) {
        super(data);
        this.status = "success";
    }
}
