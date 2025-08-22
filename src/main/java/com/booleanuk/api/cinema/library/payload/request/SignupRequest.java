package com.booleanuk.api.cinema.library.payload.request;

import lombok.Data;
import com.booleanuk.api.cinema.library.models.Role;


@Data
public class SignupRequest {
    private String username;
    private String password;
    private String role;
}

