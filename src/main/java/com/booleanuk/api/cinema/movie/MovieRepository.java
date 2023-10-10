package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
