package com.booleanuk.api.cinema.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        String name,
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        @Email(message = "Invalid email address!")
        String email,
        @NotNull(message = "Should not be null!")
        @NotBlank(message = "Should not be empty!")
        String phone
){}
