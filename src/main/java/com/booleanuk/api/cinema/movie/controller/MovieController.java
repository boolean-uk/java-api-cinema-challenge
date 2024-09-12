package com.booleanuk.api.cinema.movie.controller;

import com.booleanuk.api.cinema.movie.model.Movie;
import com.booleanuk.api.cinema.movie.model.MovieDTO;
import com.booleanuk.api.cinema.movie.repository.MovieRepository;
import com.booleanuk.api.cinema.response.ResponseInterface;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.screening.model.ScreeningDTO;
import com.booleanuk.api.cinema.screening.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostMapping
    public ResponseEntity<ResponseInterface> addMovie(@RequestBody MovieDTO movieDTO) {
        try {
            List<Screening> screenings = convertScreeningDTOList(movieDTO.getScreenings());

            Movie movie = extractMovieFromMovieDTO(movieDTO);
            movie.setScreenings(screenings);
            movie = this.movieRepository.save(movie);

            addMovieToScreenings(screenings, movie);
            saveScreenings(screenings);

            return CreatedSuccessResponse(movie);
        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @GetMapping
    public ResponseEntity<ResponseInterface> getAllMovies() {
        return OkSuccessResponse(this.movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInterface> getMovieById(@PathVariable (name = "id") int id) {
        Movie movie = findMovieById(id);

        if (findMovieById(id) == null){
            return NotFoundErrorResponse();
        }
        return OkSuccessResponse(movie);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseInterface> updateMovie(@PathVariable (name = "id") int id, @RequestBody Movie updatedMovie) throws ResponseStatusException {
        Movie movieToUpdate = findMovieById(id);
        if (movieToUpdate == null) {
            return NotFoundErrorResponse();
        }

        try {
            update(movieToUpdate, updatedMovie);
            return CreatedSuccessResponse(this.movieRepository.save(movieToUpdate));
        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInterface> deleteMovie(@PathVariable (name = "id") int id) throws ResponseStatusException {
        Movie movieToDelete = findMovieById(id);
        if (movieToDelete == null){
            return NotFoundErrorResponse();
        }
        this.movieRepository.delete(movieToDelete);
        return OkSuccessResponse(movieToDelete);
    }

    /* Screenings */
    @PostMapping("/{id}/screenings")
    public ResponseEntity<ResponseInterface> addScreening(@PathVariable (name = "id") int id, @RequestBody ScreeningDTO screeningDTO) {
        Movie movie = findMovieById(id);
        if (movie == null) {
            return NotFoundErrorResponse();
        }

        try {
            Screening screening = convertFromDTO(screeningDTO);
            screening.setMovie(movie);
            return CreatedSuccessResponse(this.screeningRepository.save(screening));
        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @GetMapping("{id}/screenings")
    public ResponseEntity<ResponseInterface> getAllScreenings(@PathVariable (name = "id") int id) {
        Movie movie = findMovieById(id);

        if (movie == null) {
            return NotFoundErrorResponse();
        }

        return OkSuccessResponse(this.screeningRepository.findScreeningsByMovie(movie));
    }


    /* Helper functions */
    private Screening convertFromDTO(ScreeningDTO screeningDTO) {
        String formattedDate = screeningDTO.getStartsAt().replace(" ", "T");
        OffsetDateTime startsAt = OffsetDateTime.parse(formattedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new Screening(screeningDTO.getScreenNumber(), screeningDTO.getCapacity(), startsAt);
    }

    private Movie findMovieById(int id) {
        return this.movieRepository.findById(id).orElse(null);
    }

    private List<Screening> convertScreeningDTOList(List<ScreeningDTO> screeningDTOList) {
        List<Screening> screenings = new ArrayList<>();
        screeningDTOList.forEach(s -> screenings.add(convertFromDTO(s)));
        return screenings;
    }

    private void update(Movie oldMovie, Movie newMovie) {
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setRating(newMovie.getRating());
        oldMovie.setDescription(newMovie.getDescription());
        oldMovie.setRuntimeMins(newMovie.getRuntimeMins());
        oldMovie.setUpdatedAt(OffsetDateTime.now());
    }

    private Movie extractMovieFromMovieDTO(MovieDTO movieDTO){
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setRating(movieDTO.getRating());
        movie.setDescription(movieDTO.getDescription());
        movie.setRuntimeMins(movieDTO.getRuntimeMins());
        return movie;
    }

    private void saveScreenings(List<Screening> screenings){
        screenings.forEach(s -> this.screeningRepository.save(s));
    }

    private void addMovieToScreenings(List<Screening> screenings, Movie movie) {
        screenings.forEach(s -> s.setMovie(movie));
    }
}
