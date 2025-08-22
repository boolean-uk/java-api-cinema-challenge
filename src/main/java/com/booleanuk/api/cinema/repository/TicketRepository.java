package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer>  {
    List<Ticket> findByCustomerIdAndScreeningId(int customerId, int screeningId);
}
