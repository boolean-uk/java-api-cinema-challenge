package com.booleanuk.api.cinema.response;

public class SuccessResponse extends Response{

    public SuccessResponse(Object object) {
        super(object, "success");
    }
}
