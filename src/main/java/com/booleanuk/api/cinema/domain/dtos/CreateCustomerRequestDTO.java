package com.booleanuk.api.cinema.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCustomerRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}