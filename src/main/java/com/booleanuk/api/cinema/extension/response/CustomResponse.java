package com.booleanuk.api.cinema.extension.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomResponse {
    private String status;
    private Object data;
}