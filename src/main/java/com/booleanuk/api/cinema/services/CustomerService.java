package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Ticket;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer updateCustomer(int id, Customer requestCustomer);

    Customer deleteCustomer(int id);
    Ticket bookATicket(int customerId, int screeningId,Ticket ticket);
    List<Ticket> getAllTickets(int customerId, int screeningId);
}
