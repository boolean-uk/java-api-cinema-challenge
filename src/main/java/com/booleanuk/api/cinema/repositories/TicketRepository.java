package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.domain.entities.Customer;
import com.booleanuk.api.cinema.domain.entities.Screening;
import com.booleanuk.api.cinema.domain.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomerAndScreening(Customer customer, Screening screening);
}
