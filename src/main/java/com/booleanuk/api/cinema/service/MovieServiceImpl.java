package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.MovieDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements IMovieService {
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    private static Movie convertToMovie(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setRating(dto.getRating());
        movie.setDescription(dto.getDescription());
        movie.setRuntimeMins(dto.getRuntimeMins());
        return movie;
    }

    private static Screening convertToScreening(Screening dto) {
        Screening screening = new Screening();
        screening.setScreenNumber(dto.getScreenNumber());
        screening.setCapacity(dto.getCapacity());
        screening.setStartsAt(dto.getStartsAt());
        return screening;
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional
    public Movie insertMovie(MovieDTO movieDTO) {
        Movie movie = convertToMovie(movieDTO);
        movie = movieRepository.save(movie);

        if(movieDTO.getScreenings() != null) {
            for(Screening screeningDTO : movieDTO.getScreenings()) {
                Screening screening = convertToScreening(screeningDTO);
                screening.setMovie(movie);
                screeningRepository.save(screening);
            }
        }

        return movie;
    }
        @Transactional
        @Override
        public Movie updateMovie(MovieDTO movieDTO) throws EntityNotFoundException {
            Optional<Movie> updatedMovie = movieRepository.findById(movieDTO.getId());
            if (updatedMovie.isEmpty())
                throw new EntityNotFoundException(Movie.class, movieDTO.getId());
            return movieRepository.save(convertToMovie(movieDTO));
        }

    @Transactional
    @Override
    public void deleteMovie(int id) throws EntityNotFoundException {
        movieRepository.deleteById(id);
    }

    @Override
    public Movie getMovieById(int id) throws EntityNotFoundException {
        Optional<Movie> movieOptional;
        movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty())throw new EntityNotFoundException(Movie.class, id);
        return movieOptional.get();
    }
}
