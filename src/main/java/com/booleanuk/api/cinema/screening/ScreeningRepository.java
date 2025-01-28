package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
}
