package com.booleanuk.api.Cinema.Repository;

import com.booleanuk.api.Cinema.Model.Screening;
import com.booleanuk.api.Cinema.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByTicketId(int customerId);
    List<Ticket> findByScreeningId(int screeningId);

    List<Ticket> findByCustomerAndScreeningId(int customerId, int screeningId);
}
