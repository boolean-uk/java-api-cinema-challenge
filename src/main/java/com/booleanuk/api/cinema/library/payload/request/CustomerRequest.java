package com.booleanuk.api.cinema.library.payload.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String name;
    private String email;
    private String phone;
}
