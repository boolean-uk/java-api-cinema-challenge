package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.dto.*;
import com.booleanuk.api.cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MoviesResponse> getAll(){
        List<MovieDto> data =  movieService.getMovies().stream()
                .map(m-> {
                    return new MovieDto(
                            m.getId(),
                            m.getTitle(),
                            m.getRating(),
                            m.getDescription(),
                            m.getRuntimeMins(),
                            m.getCreatedAt(),
                           m.getUpdatedAt());
                }).toList();

        return new ResponseEntity<>(new MoviesResponse("Success",data),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie get(@PathVariable(name="id") long id){
        return movieService.getMovie(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieResponse> update(@PathVariable(name="id") long id,@RequestBody MovieRequest movieRequest){

        Movie movie = new Movie();
        movie.setTitle(movieRequest.title());
        movie.setRating(movieRequest.rating());
        movie.setDescription(movieRequest.description());
        movie.setRuntimeMins(movieRequest.runtimeMins());

        Movie updateMovie = movieService.updateMovie(id,movie);

        MovieDto data = new MovieDto(
                updateMovie.getId(),
                updateMovie.getTitle(),
                updateMovie.getRating(),
                updateMovie.getDescription(),
                updateMovie.getRuntimeMins(),
                updateMovie.getCreatedAt(),
                updateMovie.getUpdatedAt());

        return new ResponseEntity<>(new MovieResponse("Success",data),HttpStatus.CREATED);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieResponse> create(@RequestBody @Valid MovieScreensRequest movieScreensRequest)    {

        Movie movie = new Movie();
        movie.setTitle(movieScreensRequest.title());
        movie.setRating(movieScreensRequest.rating());
        movie.setDescription(movieScreensRequest.description());
        movie.setRuntimeMins(movieScreensRequest.runtimeMins());

        if(movieScreensRequest.screenings() !=null ) {
            List<Screening> screenings = movieScreensRequest.screenings().stream()
                    .map(s -> {
                        Screening screening = new Screening();
                        screening.setScreenNumber(s.screenNumber());
                        screening.setCapacity(s.capacity());
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(s.startsAt(), formatter);
                            screening.setStartsAt(dateTime);

                        } catch (Exception e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startsAt format is invalid");
                        }

                        screening.setCreatedAt(LocalDateTime.now());
                        screening.setUpdatedAt(LocalDateTime.now());
                        screening.setMovie(movie);
                        return screening;
                    }).toList();

            movie.setScreenings(screenings);
        }
        Movie createdMovie =movieService.createMovie(movie);
        MovieDto data = new MovieDto(
                createdMovie.getId(),
                createdMovie.getTitle(),
                createdMovie.getRating(),
                createdMovie.getDescription(),
                createdMovie.getRuntimeMins(),
                createdMovie.getCreatedAt(),
                createdMovie.getUpdatedAt());
        return new ResponseEntity<>(new MovieResponse("Success",data),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MovieResponse> delete(@PathVariable(name="id") long id){
        Movie movie = movieService.deleteMovie(id);
        MovieDto data = new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getRating(),
                movie.getDescription(),
                movie.getRuntimeMins(),
                movie.getCreatedAt(),
                movie.getUpdatedAt());
        return new ResponseEntity<>(new MovieResponse("Success",data),HttpStatus.OK);
    }
    @GetMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ScreeningsResponse> getAllMovieScreenings(@PathVariable long id){

        List<ScreeningDto> data =  movieService.getScreenings(id).stream()
                .map(s-> {
                    return new ScreeningDto(
                            s.getId(),
                            s.getScreenNumber(),
                            s.getCapacity(),
                            s.getStartsAt(),
                            s.getCreatedAt(),
                            s.getUpdatedAt());
                }).toList();

        return new ResponseEntity<>(new ScreeningsResponse("Success",data),HttpStatus.OK);
    }
    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ScreeningResponse> createMovieScreening(@PathVariable int id,@Valid @RequestBody ScreeningRequest screeningRequest){

        Screening screening = new Screening();
        screening.setScreenNumber(screeningRequest.screenNumber());
        screening.setCapacity(screeningRequest.capacity());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(screeningRequest.startsAt(), formatter);
            screening.setStartsAt(dateTime);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startsAt format is invalid");
        }
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());

        Screening createdScreening = movieService.createScreening(id,screening);
        ScreeningDto data = new ScreeningDto(
                createdScreening.getId(),
                createdScreening.getScreenNumber(),
                createdScreening.getCapacity(),
                createdScreening.getStartsAt(),
                createdScreening.getCreatedAt(),
                createdScreening.getUpdatedAt());

        return new ResponseEntity<>(new ScreeningResponse("Success",data),HttpStatus.CREATED);
    }
}
