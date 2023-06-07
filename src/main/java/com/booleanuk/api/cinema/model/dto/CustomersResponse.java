package com.booleanuk.api.cinema.model.dto;

import java.util.List;

public record CustomersResponse (String status,
                                 List<CustomerData> data){
}
