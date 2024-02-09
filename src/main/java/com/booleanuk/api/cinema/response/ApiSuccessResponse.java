package com.booleanuk.api.cinema.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiSuccessResponse<T> {
    private String status;
    private T data;

    public ApiSuccessResponse(T data) {
        this.status = "success";
        this.data = data;
    }
}
