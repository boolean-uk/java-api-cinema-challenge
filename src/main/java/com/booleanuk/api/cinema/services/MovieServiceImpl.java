package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.Dtos.MovieDto;
import com.booleanuk.api.cinema.Dtos.ScreeningDto;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepo;
import com.booleanuk.api.cinema.repositories.ScreeningRepo;
import com.booleanuk.api.cinema.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    ScreeningRepo screeningRepo;

    private Movie findMovieById(int id) {
        return movieRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Movie with that id was found"));
    }

    @Override
    public Movie createMovie(MovieDto requestMovie) {
        MovieDto tmp = new MovieDto(requestMovie.getTitle(),requestMovie.getRating(),requestMovie.getDescription(),requestMovie.getRuntimeMins(),new ArrayList<>());
        if (Validation.checkIfNullExists(tmp)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not create the Movie,please check all required fields are correct.");
        }
        Movie movieFromDb = movieRepo.save(requestMovie.toMovie());
        if (requestMovie.getScreenings() != null) {
            for (ScreeningDto screening : requestMovie.getScreenings()) {
                if (Validation.checkIfNullExists(screening)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not create the Screening,please check all required fields are correct.");
                }
                screeningRepo.save(screening.toScreening(movieFromDb));
            }
        }
        return movieFromDb;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public Movie updateMovie(int id, Movie requestMovie) {
        Movie movieFromDb = findMovieById(id);
        if (requestMovie.getTitle() != null) {
            movieFromDb.setTitle(requestMovie.getTitle());
        }
        if (requestMovie.getRating() != null) {
            movieFromDb.setRating(requestMovie.getRating());
        }
        if (requestMovie.getDescription() != null) {
            movieFromDb.setDescription(requestMovie.getDescription());
        }
        if (requestMovie.getRuntimeMins() != 0) {
            movieFromDb.setRuntimeMins(requestMovie.getRuntimeMins());
        }
        return movieRepo.save(movieFromDb);
    }

    @Override
    public Movie deleteMovie(int id) {
        Movie movieFromDb = findMovieById(id);
        movieRepo.delete(movieFromDb);
        return movieFromDb;
    }

    @Override
    public Screening createScreeningForMovie(int movieId, ScreeningDto requestScreening) {
        Movie movieFromDb = findMovieById(movieId);
        if (Validation.checkIfNullExists(requestScreening)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not create the Screening,please check all required fields are correct.");
        }
        Screening tmp = requestScreening.toScreening(movieFromDb);
        return screeningRepo.save(tmp);
    }

    @Override
    public List<Screening> getAllScreeningForMovie(int movieId) {
        Movie movieFromDb = findMovieById(movieId);
        return movieFromDb.getScreenings();
    }

}
