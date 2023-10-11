package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.customer.Customer;
import com.booleanuk.api.cinema.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
