package com.booleanuk.api.cinema.tickets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findAllByCustomerIdAndScreeningId(int customerId, int screeningId);
}
