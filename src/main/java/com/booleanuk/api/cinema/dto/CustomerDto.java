package com.booleanuk.api.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CustomerDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime updatedAt;

    public CustomerDto(int id, String name, String email, String phone, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
