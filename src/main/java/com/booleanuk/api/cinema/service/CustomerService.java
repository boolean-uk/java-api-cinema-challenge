package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Ticket;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomer(Long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    Customer deleteCustomer(Long id);
    List<Ticket> getCustomerScreeningTickets(long customerId,long screeningId);
    Ticket createCustomerTicket(long customerId,long screeningId, int numSeats);
}
