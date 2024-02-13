package com.booleanuk.api.cinema.response;
import com.booleanuk.api.cinema.model.Customer;

public class CustomerResponse extends Response<Customer> {
    private Customer data;
    public CustomerResponse(Customer customer) {
        super("Success",customer);
    }
}