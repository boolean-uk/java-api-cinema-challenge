package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.WrapperUtils;
import com.booleanuk.api.cinema.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerViewDTO {

    private String status;
    private Customer data;

    public static CustomerViewDTO fromCustomer(Customer customer){
        return new CustomerViewDTO(
                WrapperUtils.STATUS.SUCCESS.status,
                customer
        );
    }
}
