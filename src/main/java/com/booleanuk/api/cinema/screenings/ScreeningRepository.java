package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.tickets.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer>{

    List<Screening> getScreeningByMovie(Movie movie);
}
