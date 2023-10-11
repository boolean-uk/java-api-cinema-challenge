package com.booleanuk.api.cinema.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId);
}
