package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
