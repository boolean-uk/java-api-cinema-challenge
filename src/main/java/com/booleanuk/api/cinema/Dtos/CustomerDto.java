package com.booleanuk.api.cinema.Dtos;

import com.booleanuk.api.cinema.entities.Customer;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CustomerDto {
    private String name;
    private String email;
    private String phone;

    public Customer toCustomer() {
        return new Customer(0, name, email, phone, null, null, new ArrayList<>());
    }
}
