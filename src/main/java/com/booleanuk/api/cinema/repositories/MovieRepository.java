package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitleAndRuntimeMins(String title, int runtimeMins);
}
