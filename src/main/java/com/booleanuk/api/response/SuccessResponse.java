package com.booleanuk.api.response;

public class SuccessResponse extends ResponseTemplate{

    public SuccessResponse(Object data) {
        super("success", data);
    }
}
