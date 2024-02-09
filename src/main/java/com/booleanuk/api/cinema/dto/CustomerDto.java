package com.booleanuk.api.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date createdAt;
    private Date updatedAt;

    public CustomerDto(int id, String name, String email, String phone, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
