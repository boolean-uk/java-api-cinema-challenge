package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static java.sql.Date.valueOf;

public class TicketTest {
    @Test
    public void testVerifyTicket()  {
        Customer customer = new Customer("Handy Manny",
                "manny@handy.com",
                "1234");
        Movie movie = new Movie("Handy Manny The Movie",
                "For kids",
                "It's about Manny",
                15);
        Date date = valueOf(LocalDate.now());
        Screening screening = new Screening(movie, 4, date, 40);

        Ticket validTicket = new Ticket(customer, screening, 2);
        Ticket invalidTicket = new Ticket();

        Assertions.assertTrue(validTicket.verifyTicket());
        Assertions.assertFalse(invalidTicket.verifyTicket());
    }
}
