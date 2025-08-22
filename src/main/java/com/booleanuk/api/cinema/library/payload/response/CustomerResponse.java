package com.booleanuk.api.cinema.library.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
public class CustomerResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
