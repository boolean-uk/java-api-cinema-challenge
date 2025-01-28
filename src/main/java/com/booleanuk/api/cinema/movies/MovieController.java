package com.booleanuk.api.cinema.movies;


import com.booleanuk.api.cinema.screens.Screen;
import com.booleanuk.api.cinema.screens.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreenRepository screenRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        List<Screen> screenList = new ArrayList<>();
        movie.setScreens(screenList);
        movie.setCreated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable(name = "id") int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie."));
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setRuntime_mins(movie.getRuntime_mins());
        movieToUpdate.setUpdated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable(name = "id") int id) {
        Movie toDelete = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie found"));
        this.screenRepository.deleteAll(toDelete.getScreens());
        this.movieRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);

    }
}
