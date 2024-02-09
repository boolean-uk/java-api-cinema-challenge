package com.booleanuk.api.cinema.Response;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;

public class CustomerResponse {

    private String status;

    private Customer data;

    public CustomerResponse(String status, Customer data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getData() {
        return data;
    }

    public void setData(Customer data) {
        this.data = data;
    }
}
