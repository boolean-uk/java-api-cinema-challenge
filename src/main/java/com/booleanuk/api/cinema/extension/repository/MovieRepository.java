package com.booleanuk.api.cinema.extension.repository;

import com.booleanuk.api.cinema.extension.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
