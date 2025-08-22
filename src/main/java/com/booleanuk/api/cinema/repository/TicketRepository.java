package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findTicketsByCustomerAndScreening(User customer, Screening screening);
}
