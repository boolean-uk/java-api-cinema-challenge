package com.booleanuk.api.cinema.ticket.repository;

import com.booleanuk.api.cinema.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
