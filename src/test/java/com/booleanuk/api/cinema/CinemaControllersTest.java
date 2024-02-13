package com.booleanuk.api.cinema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class CinemaControllersTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetAllMovies() throws Exception {
		mockMvc.perform(get("/movies"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	public void testCreateMovie() throws Exception {
		String requestBody = "{ \"title\": \"Test Movie\", \"rating\": \"PG-13\", \"description\": \"Test Description\", \"runtimeMins\": 120 }";

		mockMvc.perform(post("/movies")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("success"))
				.andExpect(jsonPath("$.data.title").value("Test Movie"))
				.andExpect(jsonPath("$.data.rating").value("PG-13"))
				.andExpect(jsonPath("$.data.description").value("Test Description"))
				.andExpect(jsonPath("$.data.runtimeMins").value(120));
	}
}
