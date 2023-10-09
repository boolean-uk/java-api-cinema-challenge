package com.booleanuk.api.cinema.model.dto;

import java.time.LocalDateTime;

public record CustomerDto(
        long id,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt){
}
