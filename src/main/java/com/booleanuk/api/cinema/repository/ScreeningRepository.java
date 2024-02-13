package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening,Integer> {
	long deleteByMovie(Movie movie);

	List<Screening> findAllByMovie(Movie movie);
}
