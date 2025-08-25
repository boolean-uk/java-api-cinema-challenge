package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CustomerResponse extends Response<Customer> {
    public CustomerResponse(Customer data) {
        super(data);
    }


}
