package com.booleanuk.api.cinema.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class DataResponse<T> {
    protected String status;
    protected T data;

    public void set(T data){
        this.status = "success";
        this.data = data;
    }
}
