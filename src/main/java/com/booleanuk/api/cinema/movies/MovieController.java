package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.generic.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies")
public class MovieController extends GenericController<Movie> {
  public MovieController(MovieRepository repository) {
    super(repository);
  }
}