package com.booleanuk.api.cinema.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessResponse<T> implements ResponseInterface  {
    private String status;
    private T data;

    public SuccessResponse(T data) {
        this.status = "success";
        this.data = data;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public T getData(){
        return data;
    }
}
