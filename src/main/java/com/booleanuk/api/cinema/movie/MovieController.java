package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.ErrorResponse;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies(){
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return new ResponseEntity<>(movieListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMovie(@RequestBody Movie movie) {

        if (isInvalidRequest(movie)) {
            return badRequest();
        }

        Movie createdMovie = this.movieRepository.save(movie);

        if(!(movie.getScreenings() == null)){
            for(Screening screening : movie.getScreenings()){
                screening.setMovie(createdMovie);
                screening.setTickets(new ArrayList<>());
                if(isInvalidScreeningRequest(screening)){
                    return badRequest();
                }
                this.screeningRepository.save(screening);
            }
        }
        else{
            movie.setScreenings(new ArrayList<>());
        }

        MovieResponse response = new MovieResponse();
        response.set(createdMovie);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.getAMovie(id);

        if(movieToDelete == null){
            return notFound();
        }

        List<Screening> screenings = movieToDelete.getScreenings();

        for (Screening screening : screenings) {
            this.screeningRepository.delete(screening);
        }

        movieToDelete.setScreenings(new ArrayList<>());
        this.movieRepository.delete(movieToDelete);

        MovieResponse response = new MovieResponse();
        response.set(movieToDelete);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> UpdateMovieById(@PathVariable int id, @RequestBody Movie movie){

        if (isInvalidRequest(movie)) {
            return badRequest();
        }

        Movie movieToUpdate = this.getAMovie(id);

        if(movieToUpdate == null){
            return notFound();
        }

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        this.movieRepository.save(movieToUpdate);

        MovieResponse response = new MovieResponse();
        response.set(movieToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Movie getAMovie(int id){
        return this.movieRepository.findById(id).orElse(null);
    }

    private boolean isInvalidRequest(Movie movie){
        return movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == null;
    }

    private boolean isInvalidScreeningRequest(Screening screening){
        return screening.getScreenNumber() == 0 || screening.getCapacity() == 0 || screening.getStartsAt() == null;
    }

    private ResponseEntity<ApiResponse<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create movie, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No movie with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
