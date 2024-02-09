package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("SELECT s FROM Ticket s WHERE s.customer.id = ?1 AND s.screening.id = ?2")
    List<Ticket> findAllByCustomerIdAndScreeningId(int customerId, int screeningId);
}
