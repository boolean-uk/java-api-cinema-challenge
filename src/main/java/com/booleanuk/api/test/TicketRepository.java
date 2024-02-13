package com.booleanuk.api.test;

import com.booleanuk.api.test.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

interface TicketRepository extends
        JpaRepository<Ticket, Integer> {
}
