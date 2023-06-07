package com.booleanuk.api.cinema.services.customer;

import com.booleanuk.api.cinema.entities.Customer;
import com.booleanuk.api.cinema.entities.Ticket;

import java.util.List;

public interface CustomerServiceInterface {

    List<Customer> getAllCustomers();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(int id, Customer customer);

    Customer deleteCustomer(int id);
    List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId);
    Ticket createTicket(int customerId, int screeningId, Ticket ticket);
}
