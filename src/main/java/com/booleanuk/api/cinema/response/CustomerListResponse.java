package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Customer;

import java.util.List;

class CustomerListResponse extends Response<List<Customer>>{

    public CustomerListResponse(List<Customer> success, List<Customer> movies) {
        super(success, movies);
    }
}