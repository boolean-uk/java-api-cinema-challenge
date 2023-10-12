package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.Dtos.CustomerDto;
import com.booleanuk.api.cinema.Dtos.TicketDto;
import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Ticket;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CustomerDto customer);

    List<Customer> getAllCustomers();

    Customer updateCustomer(int id, Customer requestCustomer);

    Customer deleteCustomer(int id);
    Ticket bookATicket(int customerId, int screeningId, TicketDto ticket);
    List<Ticket> getAllTickets(int customerId, int screeningId);
}
