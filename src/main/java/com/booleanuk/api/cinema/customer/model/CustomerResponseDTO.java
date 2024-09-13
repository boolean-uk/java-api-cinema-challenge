package com.booleanuk.api.cinema.customer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor

public class CustomerResponseDTO {

    public CustomerResponseDTO (int id, String name, String email, String phone, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private int id;

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
