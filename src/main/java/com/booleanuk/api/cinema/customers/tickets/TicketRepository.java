package com.booleanuk.api.cinema.customers.tickets;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}