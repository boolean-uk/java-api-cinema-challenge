package com.booleanuk.api.cinema.library.payload.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
