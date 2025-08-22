package com.booleanuk.api.cinema.movie.controller;

import com.booleanuk.api.cinema.mapper.MovieMapper;
import com.booleanuk.api.cinema.movie.model.Movie;
import com.booleanuk.api.cinema.movie.model.MovieRequestDTO;
import com.booleanuk.api.cinema.movie.model.MovieResponseDTO;
import com.booleanuk.api.cinema.movie.model.MovieUpdateDTO;
import com.booleanuk.api.cinema.movie.repository.MovieRepository;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.ResponseFactory;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.screening.repository.ScreeningRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.booleanuk.api.cinema.response.ResponseFactory.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    MovieMapper movieMapper;

    @PostMapping
    public ResponseEntity<Response> addMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO, BindingResult result) {
        if (result.hasErrors()){
            return badRequestErrorResponse();
        }

        Movie movie = movieMapper.toEntity(movieRequestDTO);

        // Set movie to each screening.
        if (movie.getScreenings() != null) {
            movie.getScreenings().forEach(s -> s.setMovie(movie));
        }

        // Save movie
        Movie savedMovie = this.movieRepository.save(movie);

        // Convert to DTO without screenings
        MovieResponseDTO responseDTO = movieMapper.toResponseDTO(savedMovie);

        return createdSuccessResponse(responseDTO);
    }


    @GetMapping
    public ResponseEntity<Response> getAllMovies() {
        List<MovieResponseDTO> responseList = new ArrayList<>();
        this.movieRepository.findAll().forEach(movie -> responseList.add(movieMapper.toResponseDTO(movie)));
        return okSuccessResponse(responseList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMovie(@PathVariable (name = "id") int id,
                                                @Valid @RequestBody MovieUpdateDTO updatedMovieDTO,
                                                BindingResult result) {

        if (result.hasErrors()) {
            return badRequestErrorResponse();
        }

        return this.movieRepository.findById(id)
                .map(movieToUpdate -> {
                    updateMovieDetails(movieToUpdate, updatedMovieDTO);
                    Movie savedMovie = this.movieRepository.save(movieToUpdate);
                    MovieResponseDTO responseDTO = movieMapper.toResponseDTO(savedMovie);
                    return createdSuccessResponse(responseDTO);
                }).orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMovie(@PathVariable (name = "id") int id) {
        return this.movieRepository.findById(id)
                .map(movie -> {
                    this.movieRepository.delete(movie);
                    MovieResponseDTO responseDTO = movieMapper.toResponseDTO(movie);
                    return okSuccessResponse(responseDTO);
                })
                .orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    /* Screenings */
    @PostMapping("/{id}/screenings")
    public ResponseEntity<Response> addScreening(@PathVariable (name = "id") int id,
                                                 @Valid @RequestBody Screening screening,
                                                 BindingResult result) {

        if (result.hasErrors()) {
            return badRequestErrorResponse();
        }

        return this.movieRepository.findById(id).map(movie -> {
            screening.setMovie(movie);
            return createdSuccessResponse(this.screeningRepository.save(screening));
        }).orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    @GetMapping("{id}/screenings")
    public ResponseEntity<Response> getAllScreenings(@PathVariable (name = "id") int id) {
        return this.movieRepository.findById(id)
                .map(movie -> okSuccessResponse(this.screeningRepository.findScreeningsByMovie(movie)))
                    .orElseGet(ResponseFactory::notFoundErrorResponse);
    }

    /* Helper functions */
    private void updateMovieDetails(Movie oldMovie, MovieUpdateDTO newMovie) {
        if (newMovie.getTitle() != null) {
            oldMovie.setTitle(newMovie.getTitle());
        }

        if (newMovie.getRating() != null) {
            oldMovie.setRating(newMovie.getRating());
        }

        if (newMovie.getDescription() != null) {
            oldMovie.setDescription(newMovie.getDescription());
        }

        if (newMovie.getRuntimeMins() != null) {
            oldMovie.setRuntimeMins(newMovie.getRuntimeMins());
        }

        oldMovie.setUpdatedAt(OffsetDateTime.now());
    }

    private MovieResponseDTO convertToResponseDTO(Movie movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getRating(),
                movie.getDescription(),
                movie.getRuntimeMins(),
                movie.getCreatedAt(),
                movie.getUpdatedAt());
    }
}
