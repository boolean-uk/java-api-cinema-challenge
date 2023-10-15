package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.GenericResponse;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScreeningService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(MovieRepository movieRepository, ScreeningRepository screeningRepository){
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }
    public Screening create(int movieId, Screening screening){
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        screening.setMovie(movie);
        try {return screeningRepository.save(screening);}
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create screening, please check all required fields are correct");
        }
    }

    /**
     * Returns the record from the database if found
     * else throws ResponseStatusException with a 404 code**/
    public Screening getById(int id){
        return screeningRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screenings matching that id were found"));
    }

    public List<Screening> getAllForMovie(int movieId){

        return screeningRepository.findByMovieId(movieId);
    }
}
