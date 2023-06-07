package com.booleanuk.api.cinema.services.movie;

import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.repositories.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class IMovieService implements MovieServiceInterface {
    @Autowired
    MovieRepo movieRepo;
    @Override
    public Movie createMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    @Override
    public List<Movie> getAllMovie() {
        return movieRepo.findAll();
    }
    private Movie findByIdOrElseThrow(int id){
        return movieRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"No Movie with that id were found"));
    }

    @Override
    public Movie updateMovie(int id, Movie movie) {
        Movie movieToUpdate = findByIdOrElseThrow(id);
        if (movie.getTitle()!=null){movieToUpdate.setTitle(movie.getTitle());}
        if (movie.getRating()!=null){movieToUpdate.setRating(movie.getRating());}
        if (movie.getDescription()!=null){movieToUpdate.setDescription(movie.getDescription());}
        if (movie.getRuntimeMins()!=0){movieToUpdate.setRuntimeMins(movie.getRuntimeMins());}
        return movieRepo.save(movieToUpdate);
    }

    @Override
    public Movie deleteMovie(int id) {
        Movie movieTodelete = findByIdOrElseThrow(id);
        movieRepo.delete(movieTodelete);
        return movieTodelete;
    }
}
