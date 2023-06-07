package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByCustomerIdAndScreeningId(int customerId,int screeningId);
}
