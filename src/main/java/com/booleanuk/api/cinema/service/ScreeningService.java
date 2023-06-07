package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.ScreeningViewDTO;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    public List<ScreeningViewDTO> getAllForMovie(int movieId){
        List<Screening> screenings = screeningRepository.findByMovieId(movieId);
        List<ScreeningViewDTO> screeningViewDTOS = new ArrayList<>();

        screenings.forEach(screening -> {
            screeningViewDTOS.add(ScreeningViewDTO.fromScreening(screening));
        });

        return screeningViewDTOS;
    }
}
