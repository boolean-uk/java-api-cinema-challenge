package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findAllByCustomerIdAndScreeningId(int customerId, int screeningId);

}
