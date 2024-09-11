package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.dtos.ResponseDTO;
import com.booleanuk.api.cinema.exceptions.BadRequestException;
import com.booleanuk.api.cinema.exceptions.NotFoundException;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Movie movie) {
        try{

            Movie savedMovie = movieRepository.save(movie);

            if(movie.getScreenings() != null) {
                List<Screening> screenings =  new ArrayList<>();

                for(Screening screeningGet : movie.getScreenings()) {
                    Screening screening = new Screening();
                    screening.setScreenNumber(screeningGet.getScreenNumber());
                    screening.setCapacity(screeningGet.getCapacity());
                    screening.setStartsAt(screeningGet.getStartsAt());
                    screening.setMovie(savedMovie);
                    screenings.add(screening);
                }

                screeningRepository.saveAll(screenings);
            }
            ResponseDTO<Movie> response = new ResponseDTO<>(
                    "success",
                    movie);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Movie movie, @PathVariable int id) {
        Movie movieToUpdate = findById(id);

        try{
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            this.movieRepository.save(movieToUpdate);
            ResponseDTO<Movie> response = new ResponseDTO<>(
                    "success",
                    movieToUpdate);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            throw new BadRequestException("bad request");
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Movie movie = findById(id);
        this.movieRepository.delete(movie);

        ResponseDTO<Movie> response = new ResponseDTO<>(
                "success",
                movie);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    private Movie findById(int id) {
        return this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Movie not found with ID " + id)
        );

    }

}
