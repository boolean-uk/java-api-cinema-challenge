package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Test
    public void testGetAllMovies() throws Exception {
        screeningRepository.deleteAll();
        movieRepository.deleteAll();
        movieRepository.save(new Movie("Dracula", "PG-13", "vampires", 120));
        movieRepository.save(new Movie("Dracula 2", "R", "vampires", 150));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Dracula"))
                .andExpect(jsonPath("$.data[1].title").value("Dracula 2"));
    }

    @Test
    public void testCreateAMovie() throws Exception {
        Movie validRequestBody = new Movie("Valid Movie", "PG", "Valid Movie Description", 110);

        HashMap<String, String> invalidRequestBody = new HashMap<>();
        invalidRequestBody.put("title", "new invalid movie");
        invalidRequestBody.put("rating", "PG");
        invalidRequestBody.put("description", "long description");

        String validJsonMovie = objectMapper.writeValueAsString(validRequestBody);
        String invalidJsonMovie = objectMapper.writeValueAsString(invalidRequestBody);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonMovie))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.title").value("Valid Movie"))
                .andExpect(jsonPath("$.status").value("success"));

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonMovie))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("bad request"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateAMovieWithScreenings() throws Exception {
        String requestBody = "{" +
                "\"title\": \"Dodgeball\"," +
                "\"rating\": \"PG-13\"," +
                "\"description\": \"The greatest movie ever made.\"," +
                "\"runtimeMins\": 126," +
                "\"screenings\": [" +
                "{" +
                "\"screenNumber\": 5," +
                "\"capacity\": 40," +
                "\"startsAt\": \"2023-03-19 11:30:00+00:00\"" +
                "}" +
                "]" +
                "}";


        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").isNumber()) // Assuming id is an auto-generated number
                .andExpect(jsonPath("$.data.title").value("Dodgeball"))
                .andExpect(jsonPath("$.data.rating").value("PG-13"))
                .andExpect(jsonPath("$.data.description").value("The greatest movie ever made."))
                .andExpect(jsonPath("$.data.runtimeMins").value(126))
                .andExpect(jsonPath("$.data.screenings").doesNotExist());

        mockMvc.perform(get("/movies/{id}/screenings", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].screenNumber").value(5))
                .andExpect(jsonPath("$.data[0].startsAt").value("2023-03-19T12:30:00.000+01:00"))
                .andExpect(jsonPath("$.data[0].capacity").value(40));
    }

    @Test
    public void testUpdateMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Dracual", "no rating", "vampires", 120));

        HashMap<String, String> update = new HashMap<>();
        update.put("title", "Dracula");
        update.put("rating", "PG");
        update.put("description", "long description");

        String requestBody = objectMapper.writeValueAsString(update);

        mockMvc.perform(put("/movies/{id}", movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.title").value("Dracula"))
                .andExpect(jsonPath("$.data.rating").value("PG"))
                .andExpect(jsonPath("$.data.description").value("long description"))
                .andExpect(jsonPath("$.data.runtimeMins").value(120));

        movieRepository.delete(movie);

        mockMvc.perform(put("/movies/{id}", movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("not found"));
    }

    @Test
    public void testDeleteMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Dracula", "PG", "long description", 120));

        mockMvc.perform(delete("/movies/{id}", movie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.title").value("Dracula"))
                .andExpect(jsonPath("$.data.rating").value("PG"))
                .andExpect(jsonPath("$.data.description").value("long description"))
                .andExpect(jsonPath("$.data.runtimeMins").value(120));


        mockMvc.perform(delete("/movies/{id}", movie.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("not found"));
    }


}
