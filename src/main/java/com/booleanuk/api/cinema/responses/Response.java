package com.booleanuk.api.cinema.responses;

import com.booleanuk.api.cinema.enums.ResponseStatus;
import lombok.Getter;

@Getter
public class Response<T> {
    protected ResponseStatus status;
    protected T data;

    public void set(T data) {
        this.status = ResponseStatus.SUCCESS;
        this.data = data;
    }
}