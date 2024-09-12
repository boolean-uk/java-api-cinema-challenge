package com.booleanuk.api.cinema.ticket.repository;

import com.booleanuk.api.cinema.customer.model.Customer;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findAllByCustomerAndScreening(Customer customer, Screening screening);
}
