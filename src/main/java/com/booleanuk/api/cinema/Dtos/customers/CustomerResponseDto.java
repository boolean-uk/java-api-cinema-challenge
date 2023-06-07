package com.booleanuk.api.cinema.Dtos.customers;

import com.booleanuk.api.cinema.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponseDto {
    private String status;
    private Customer data;
}
