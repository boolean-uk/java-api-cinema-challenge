package com.booleanuk.api.cinema.Screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.booleanuk.api.cinema.Movie.Movie;


// Rest of your code...


@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findAllByMovieId(Long movieId);
}




