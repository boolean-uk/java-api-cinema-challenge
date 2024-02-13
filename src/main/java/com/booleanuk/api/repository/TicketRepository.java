package com.booleanuk.api.repository;
import com.booleanuk.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByCustomerIdAndScreeningId(int customer_id, int screening_id);

}

