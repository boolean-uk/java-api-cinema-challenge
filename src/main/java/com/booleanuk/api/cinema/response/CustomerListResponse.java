package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Customer;
import java.util.List;

public class CustomerListResponse extends Response<List<Customer>> {
    private List<Customer> data;
    public CustomerListResponse(List<Customer> data) {
        super("success", data);
    }
}

