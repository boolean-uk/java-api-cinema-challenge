package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findByCustomer_IdAndScreening_Id(long customerId, long screeningId);
}
