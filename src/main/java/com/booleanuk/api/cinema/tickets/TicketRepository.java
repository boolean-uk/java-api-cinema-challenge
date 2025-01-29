package com.booleanuk.api.cinema.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.screenings.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    List<Ticket> findAllTicketsByScreeningAndCustomer(Screening screening, Customer customer);
}
