package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MovieServiceImp implements MovieService {
    @Autowired
    private MovieRepository moviesRepository;
    @Override
    public List<Movie> getMovies() {
        return moviesRepository.findAll();
    }

    @Override
    public Movie getMovie(Long id) {
        return moviesRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie matches the provided id"));
    }

    @Override
    public Movie createMovie(Movie movie)
    {
        LocalDateTime createdAt =LocalDateTime.now();
        movie.setCreatedAt(createdAt);
        movie.setUpdatedAt(createdAt);
        return moviesRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Movie foundMovie = moviesRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie matches the provided id"));

        movie.setId(id);
        movie.setCreatedAt(foundMovie.getCreatedAt());
        movie.setUpdatedAt(LocalDateTime.now());
        return moviesRepository.save(movie);
    }

    @Override
    public Movie deleteMovie(Long id) {
        Movie toDelete = moviesRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie matches the provided id"));

        moviesRepository.delete(toDelete);
        return toDelete;
    }
}
