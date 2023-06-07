package com.booleanuk.api.cinema.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    String movieId = "1";
    String url = "/movies/{id}";

    @Test
    void shouldReturnAMovie_WhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, movieId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext context = JsonPath.parse(response.getBody());
        //TODO: find Id from db
        Number id = context.read("$.id");
        assertThat(id).isNotNull();
    }
}
