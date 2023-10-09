package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId);
    List<Ticket> findByCustomerId(int customerId);
    List<Ticket> findByScreeningId(int screeningId);
}