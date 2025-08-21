package com.booleanuk.api.cinema.library.repository;

import com.booleanuk.api.cinema.library.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCustomerId(int customerId);
    List<Ticket> findByScreeningId(int screeningId);
}
