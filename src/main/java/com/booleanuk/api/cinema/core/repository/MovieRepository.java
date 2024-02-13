package com.booleanuk.api.cinema.core.repository;

import com.booleanuk.api.cinema.core.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
