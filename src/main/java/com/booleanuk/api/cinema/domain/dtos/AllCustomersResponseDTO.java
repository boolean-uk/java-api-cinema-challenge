package com.booleanuk.api.cinema.domain.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AllCustomersResponseDTO {
    private List<CustomerResponseDTO> customers;

}