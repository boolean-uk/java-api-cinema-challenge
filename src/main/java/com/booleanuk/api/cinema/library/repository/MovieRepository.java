package com.booleanuk.api.cinema.library.repository;

import com.booleanuk.api.cinema.library.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
