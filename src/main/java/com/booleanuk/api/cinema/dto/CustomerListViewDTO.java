package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.WrapperUtils;
import com.booleanuk.api.cinema.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerListViewDTO {
    private String status;
    private List<Customer> data;

    public static CustomerListViewDTO fromCustomers(List<Customer> customers){
        return new CustomerListViewDTO(
                WrapperUtils.STATUS.SUCCESS.status,
                customers
        );
    }
}
