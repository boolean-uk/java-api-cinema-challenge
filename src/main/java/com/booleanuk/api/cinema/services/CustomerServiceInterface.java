package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.Dtos.CustomerDtoWithoutTickets;
import com.booleanuk.api.cinema.entities.Customer;

import java.util.List;

public interface CustomerServiceInterface {

    List<CustomerDtoWithoutTickets> generateList();
}
