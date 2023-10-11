package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.ScreeningDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScreeningServiceImpl implements IScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    private static Screening convertToScreening(ScreeningDTO dto) {
        return new Screening(dto.getId(), dto.getScreenNumber(), dto.getCapacity(), dto.getStartsAt(), dto.getCreatedAt(), dto.getUpdatedAt());
    }

    @Override
    public List<Screening> getScreenings(int movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class, movieId));
        List<Screening> screenings = screeningRepository.findByMovieId(movie.getId());
        if (screenings.isEmpty()) throw new EntityNotFoundException(Screening.class, movieId);
        return screenings;
    }

    @Override
    @Transactional
    public Screening insertScreening(int movieId, ScreeningDTO screeningDTO) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException(Movie.class, movieId));
        Screening screening = convertToScreening(screeningDTO);
        screening.setMovie(movie);
        return screeningRepository.save(screening);
    }
}
