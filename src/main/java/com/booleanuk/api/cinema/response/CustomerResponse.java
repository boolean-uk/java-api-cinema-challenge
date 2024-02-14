package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Customer;

public class CustomerResponse extends Response<Customer>{
    public CustomerResponse(Customer success, Customer movies) {
        super(success, movies);
    }

    public CustomerResponse() {
        super();
    }
}