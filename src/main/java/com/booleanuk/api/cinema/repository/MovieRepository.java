package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
