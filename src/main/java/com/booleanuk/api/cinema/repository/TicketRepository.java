package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByScreeningId (int id);
    List<Ticket> findByCustomerId (int id);

    List<Ticket> findByCustomerIdAndScreeningId(int customer_id, int screening_id);
}
