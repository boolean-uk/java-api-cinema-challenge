package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.dto.TicketDto;
import com.booleanuk.api.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<TicketDto> findByCustomerIdAndScreeningId(int customerId, int screeningId);
    List<TicketDto> findByCustomerId(int customerId);
}
