package com.booleanuk.api.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.booleanuk.api.cinema.models.Movie;


public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
