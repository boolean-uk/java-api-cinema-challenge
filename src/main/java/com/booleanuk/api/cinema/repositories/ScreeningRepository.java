package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> getScreeningByMovie(Movie movie);
}
