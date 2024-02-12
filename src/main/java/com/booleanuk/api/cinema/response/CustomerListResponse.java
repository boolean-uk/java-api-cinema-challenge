package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.customer.Customer;

import java.util.List;

public class CustomerListResponse extends Response<List<Customer>> {

    public CustomerListResponse(List<Customer> data) {
        super("success", data);
    }
}