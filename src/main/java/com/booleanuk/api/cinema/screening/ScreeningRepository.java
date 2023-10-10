package com.booleanuk.api.cinema.screening;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findByMovieId(int movieId);

    Screening findByScreenNumber(int screenNumber);
}
