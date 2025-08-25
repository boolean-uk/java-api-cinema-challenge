package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CustomerListResponse extends Response<List<Customer>>{

    public CustomerListResponse(List<Customer> data) {
        super(data);
    }
}
