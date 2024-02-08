package com.booleanuk.api.cinema.screenings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    List<Screening> findByMovieId(int movieId);
}
