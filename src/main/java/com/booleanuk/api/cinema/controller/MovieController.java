package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.MovieDTO;
import com.booleanuk.api.cinema.utility.responses.MovieListResponse;
import com.booleanuk.api.cinema.utility.responses.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAll(){
        MovieListResponse movieResponse = new MovieListResponse();
        movieResponse.set(this.movieRepository.findAll(Sort.by(Sort.Direction.ASC, "movieId")));
        return ResponseEntity.ok(movieResponse);
    }

    private Movie getMovie(int id){
        return this.movieRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<DataResponse<?>> create(@RequestBody MovieDTO movieDTO){
        Movie movieCreated;
        try {
            Movie movie = new Movie();
            movie.setTitle(movieDTO.title());
            movie.setRating(movieDTO.rating());
            movie.setDescription(movieDTO.description());
            movie.setRuntimeMinutes(movieDTO.runtimeMinutes());
            movie.setCreatedAt(ZonedDateTime.now());
            movie.setUpdatedAt(ZonedDateTime.now());

            movieCreated = this.movieRepository.save(movie);
            List<Screening> screenings = movieDTO.screenings();
            if(screenings != null){
                String url = "http://localhost:4000/movies/"+movieCreated.getMovieId()+"/screenings";
                RestTemplate restTemplate = new RestTemplate();
                for (Screening screening : screenings){
                    restTemplate.postForEntity(url, screening, String.class);
                }
            }
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create new movie");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieCreated);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<?>> update(@PathVariable int id, @RequestBody Movie movie){
        Movie updateMovie = this.getMovie(id);
        if(updateMovie == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Cant find movie with this id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        try {
            updateMovie.setTitle(movie.getTitle());
            updateMovie.setRating(movie.getRating());
            updateMovie.setDescription(movie.getDescription());
            updateMovie.setRuntimeMinutes(movie.getRuntimeMinutes());
            updateMovie.setUpdatedAt(ZonedDateTime.now());
            updateMovie = this.movieRepository.save(updateMovie);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Cant update the movie chosen");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(updateMovie);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<?>> update(@PathVariable int id){
        Movie deleteMovie = this.getMovie(id);
        if(deleteMovie == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Cant find movie with that id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        List<Screening> screenings = deleteMovie.getScreenings();
        for (Screening screening : screenings){
            screening.setMovie(null);
        }
        this.movieRepository.delete(deleteMovie);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(deleteMovie);
        return ResponseEntity.ok(movieResponse);
    }
}
