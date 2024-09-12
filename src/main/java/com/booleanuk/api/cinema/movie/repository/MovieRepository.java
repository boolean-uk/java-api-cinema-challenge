package com.booleanuk.api.cinema.movie.repository;

import com.booleanuk.api.cinema.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
