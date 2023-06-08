package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    MovieRepository movieRepository;
    Long movieId = null;
    String url = "/movies/{id}";

    @BeforeEach
    public void setUp() {
        Iterable<Movie> movies = movieRepository.findAll();
        Iterator<Movie> iterator = movies.iterator();
        movieId = iterator.hasNext() ? iterator.next().getId() : null;
    }

    @Test
    void shouldReturnAMovie_WhenGetRequest() {
        if (movieId == null) return;
        System.out.println(movieId);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, movieId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext context = JsonPath.parse(response.getBody());
        //TODO: find Id from db
        Number id = context.read("$.id");
        assertThat(id).isNotNull();
    }

    @Test
    void shouldCreateANewMovie_WhenPostRequest() {
        Movie movie = new Movie(
                "Inception",
                "PG-13",
                "A thief who steals corporate secrets through the use of dream-sharing technology is given the " +
                        "inverse task of planting an idea into the mind of a C.E.O.",
                148
        );

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/movies", movie, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewMovie = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewMovie, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnAllMovies_WhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/movies", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
