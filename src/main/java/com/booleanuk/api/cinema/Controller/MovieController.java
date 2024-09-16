package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.ResponseWrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ResponseWrapper<Object>> create(@RequestBody Movie newMovie) {

        try {


            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            newMovie.setCreatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));
            newMovie.setUpdatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));
            newMovie.setScreenings(new ArrayList<Screening>());
            Movie savedMovie = this.movieRepository.save(newMovie);
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("id", savedMovie.getId());
                response.put("title", newMovie.getTitle());
                response.put("description", newMovie.getDescription());
                response.put("runtimeMins", newMovie.getRuntimeMins());
                response.put("createdAt", currentDateTime.format(formatter));
                response.put("updatedAt", currentDateTime.format(formatter));
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", response));

        } catch (Exception e) {
            //System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not create movie, please check all required fields are correct."));

        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseWrapper getAll() {
        return new ResponseWrapper<>("success", this.movieRepository.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<ResponseWrapper<Object>> update(@PathVariable("id") Integer id, @RequestBody Movie updatedMovie) {
        Optional<Movie> existingMovieOptional = this.movieRepository.findById(id);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        if (existingMovieOptional.isPresent()) {
            try {
                Movie existingMovie = existingMovieOptional.get();
                existingMovie.setTitle(updatedMovie.getTitle());
                existingMovie.setRating(updatedMovie.getRating());
                existingMovie.setDescription(updatedMovie.getDescription());
                existingMovie.setRuntimeMins(updatedMovie.getRuntimeMins());
                existingMovie.setUpdatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));

                Movie savedMovie = this.movieRepository.save(existingMovie);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", savedMovie));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not update movie, please check all fields are correct."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("error", "No movie with that id found."));
            //return new ResponseWrapper<>("error", "No movie with that id found.");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Optional<Movie> delete(@PathVariable("id") Integer id) {
        Optional<Movie> movieOptional = this.movieRepository.findById(id);

        if (movieOptional.isPresent()) {
            Movie deletedMovie = movieOptional.get();
            this.movieRepository.deleteById(id);
         //   return ResponseEntity.ok(new ResponseWrapper<>("success", deletedMovie));
            return movieOptional;
        } else {
            return Optional.empty();
            // ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("error", "No movie with that id found."));
        }
    }
}
