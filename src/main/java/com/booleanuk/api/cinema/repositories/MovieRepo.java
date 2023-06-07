package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie,Integer> {
}
