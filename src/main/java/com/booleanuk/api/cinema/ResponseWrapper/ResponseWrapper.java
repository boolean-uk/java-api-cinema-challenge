package com.booleanuk.api.cinema.ResponseWrapper;

import lombok.Getter;
import lombok.Setter;

public class ResponseWrapper <T>{
    @Setter
    @Getter
    private String status;
    @Setter
    @Getter
    private T data;
    public ResponseWrapper(String status, T data) {
        this.status = status;
        this.data = data;
    }

}
