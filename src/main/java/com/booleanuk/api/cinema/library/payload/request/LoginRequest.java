package com.booleanuk.api.cinema.library.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

