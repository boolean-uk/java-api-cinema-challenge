package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.dto.TicketDto;
import com.booleanuk.api.cinema.dto.TicketNiceDto;
import com.booleanuk.api.cinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<TicketDto> findByCustomerIdAndScreeningId(int customerId, int screeningId);
    List<TicketDto> findByCustomerId(int customerId);

    @Query("SELECT new com.booleanuk.api.cinema.dto.TicketNiceDto(m.title, m.rating, m.description, m.runtimeMins, s.screenNumber, s.startsAt, t.numSeats) " +
            "FROM Ticket t " +
            "INNER JOIN t.customer c " +
            "INNER JOIN t.screening s " +
            "INNER JOIN s.movie m " +
            "WHERE m.id = :movieId AND c.id = :customerId")
    List<TicketNiceDto> findTicketsByMovieId(@Param("customerId") int customerId, @Param("movieId") int movieId);

    @Query("SELECT new com.booleanuk.api.cinema.dto.TicketNiceDto(m.title, m.rating, m.description, m.runtimeMins, s.screenNumber, s.startsAt, t.numSeats) " +
            "FROM Ticket t " +
            "JOIN t.screening s " +
            "JOIN s.movie m " +
            "JOIN t.customer c " +
            "WHERE c.id = :customerId")
    List<TicketNiceDto> findTicketsByCustomerId(@Param("customerId") int customerId);
}

