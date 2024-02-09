package com.booleanuk.api.cinema.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiErrorResponse {
    private String status;
    private ErrorResponseMessage data;

    public ApiErrorResponse(ErrorResponseMessage data) {
        this.status = "error";
        this.data = data;
    }
}