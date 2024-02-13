package com.booleanuk.api.cinema.extension.repository;

import com.booleanuk.api.cinema.extension.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
