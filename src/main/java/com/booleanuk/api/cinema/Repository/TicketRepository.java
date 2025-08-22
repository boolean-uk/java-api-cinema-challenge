package com.booleanuk.api.cinema.Repository;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
