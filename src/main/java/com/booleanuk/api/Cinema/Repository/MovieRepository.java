package com.booleanuk.api.Cinema.Repository;

import com.booleanuk.api.Cinema.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
