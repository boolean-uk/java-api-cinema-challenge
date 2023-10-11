package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.MovieDTO;
import com.booleanuk.api.cinema.dto.ScreeningDTO;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.service.IMovieService;
import com.booleanuk.api.cinema.service.IScreeningService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MoviesScreeningsController {

    private final IScreeningService screeningService;
    private final IMovieService movieService;
    @Autowired
    public MoviesScreeningsController(IScreeningService screeningService, IMovieService movieService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
    }

    private List<ScreeningDTO> convertScreeningsToDTOs(List<Screening> screenings) {
        List<ScreeningDTO> screeningDTOS = new ArrayList<>();
        for (Screening screening : screenings) {
            screeningDTOS.add(new ScreeningDTO(screening.getId(), screening.getScreenNumber(), screening.getCapacity(),
                    screening.getStartsAt(), screening.getCreatedAt(), screening.getUpdatedAt()));
        }
        return screeningDTOS;
    }

    private List<MovieDTO> convertMoviesToDTOs(List<Movie> movies) {
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            movieDTOS.add(new MovieDTO(movie.getId(), movie.getTitle(), movie.getRating(),
                    movie.getDescription(), movie.getRuntimeMins(), movie.getCreatedAt(), movie.getUpdatedAt()));
        }
        return movieDTOS;
    }

    private MovieDTO map(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setRating(movie.getRating());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setRuntimeMins(movie.getRuntimeMins());
        movieDTO.setCreatedAt(movie.getCreatedAt());
        movieDTO.setUpdatedAt(movie.getUpdatedAt());
        return movieDTO;
    }

    private ScreeningDTO map (Screening screening) {
        ScreeningDTO screeningDTO = new ScreeningDTO();
        screeningDTO.setId(screening.getId());
        screeningDTO.setScreenNumber(screening.getScreenNumber());
        screeningDTO.setCapacity(screening.getCapacity());
        screeningDTO.setStartsAt(screening.getStartsAt());
        screeningDTO.setCreatedAt(screening.getCreatedAt());
        screeningDTO.setUpdatedAt(screening.getUpdatedAt());
        return screeningDTO;
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<List<ScreeningDTO>> getScreenings(@PathVariable int id) {
        List<Screening> screenings = screeningService.getScreenings(id);
        List<ScreeningDTO> screeningDTOS = convertScreeningsToDTOs(screenings);
        return new ResponseEntity<>(screeningDTOS, HttpStatus.OK);
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<ScreeningDTO> addScreening(@PathVariable int id, @RequestBody @Valid ScreeningDTO dto) {
        Screening screening = screeningService.insertScreening(id, dto);
        ScreeningDTO screeningDTO = map(screening);
        return new ResponseEntity<>(screeningDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovies() {
        List<Movie> movies = movieService.getMovies();
        List<MovieDTO> movieDTOS = convertMoviesToDTOs(movies);
        return new ResponseEntity<>(movieDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody @Valid MovieDTO dto) {
        Movie movie = movieService.insertMovie(dto);
        MovieDTO movieDTO = map(movie);
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable int id, @RequestBody @Valid MovieDTO dto) {
        dto.setId(id);
        Movie movie = movieService.updateMovie(dto);
        MovieDTO movieDTO = map(movie);
        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDTO> deleteMovie(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        movieService.deleteMovie(id);
        MovieDTO movieDTO = map(movie);
        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }

}
