package com.booleanuk.api.cinema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CinemaControllersTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCustomerss() throws Exception {
		String requestBody;

		//Create 400
		requestBody = "{}";
		mockMvc.perform(post("/customers").
						contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).andExpect(status().
						isBadRequest()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("bad request"));

		//Create 200
		requestBody = "{\"name\": \"Chris Wolstenholme\",\n" + "  \"email\": \"chris@muse.mu\",\n" + "  \"phone\": \"+44729388192\"\n" + "}";
		mockMvc.perform(post("/customers").
						contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).
				andExpect(status().isCreated()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.name").value("Chris Wolstenholme")).
				andExpect(jsonPath("$.data.email").value("chris@muse.mu")).
				andExpect(jsonPath("$.data.phone").value("+44729388192"));

		//GetAll 200
		mockMvc.perform(get("/customers")).
				andExpect(status().isOk()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data").isArray());

		//Update 404
		requestBody = "{\n" + "  \"name\": \"John Wolstenholme\",\n" + "  \"phone\": \"+44729388192\"\n" + "}";
		mockMvc.perform(put("/customers/10").
						contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).
				andExpect(status().isNotFound()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Update 400
		requestBody = "{}";
		mockMvc.perform(put("/customers/1").
						contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).
				andExpect(status().isBadRequest()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("bad request"));

		//Update 200
		requestBody = "{\n" + "  \"name\": \"John Wolstenholme\",\n" + "  \"phone\": \"+44729388192\"\n" + "}";
		mockMvc.perform(put("/customers/1").
						contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).
				andExpect(status().isCreated()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.name").value("John Wolstenholme")).
				andExpect(jsonPath("$.data.email").value("chris@muse.mu")).
				andExpect(jsonPath("$.data.phone").value("+44729388192"));

		//Delete 404
		mockMvc.perform(delete("/customers/10")).
				andExpect(status().isNotFound()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Delete 200
		mockMvc.perform(delete("/customers/1")).andExpect(status().isOk()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.name").value("John Wolstenholme")).
				andExpect(jsonPath("$.data.email").value("chris@muse.mu")).
				andExpect(jsonPath("$.data.phone").value("+44729388192"));
	}

	@Test
	public void testMovies() throws Exception {
		String requestBody;

		//Create 400
		requestBody = "{ \"title\": \"Test Movie\", \"rating\": \"PG-13\"}";
		mockMvc.perform(post("/movies").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isBadRequest()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("bad request"));

		//Create 200
		requestBody = "{ \"title\": \"Test Movie\", \"rating\": \"PG-13\", \"description\": \"Test Description\", \"runtimeMins\": 120 }";
		mockMvc.perform(post("/movies").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isCreated()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.title").value("Test Movie")).
				andExpect(jsonPath("$.data.rating").value("PG-13")).
				andExpect(jsonPath("$.data.description").value("Test Description")).
				andExpect(jsonPath("$.data.runtimeMins").value(120));

		//GetAll 200
		mockMvc.perform(get("/movies")).
				andExpect(status().isOk()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data").isArray());


		//Update 404
		requestBody = "{ \"title\": \"Test Movie 2\", \"description\": \"New Test Description\", \"runtimeMins\": 120 }";
		mockMvc.perform(put("/movies/10").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isNotFound()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Update 400
		requestBody = "{}";
		mockMvc.perform(put("/movies/1").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isBadRequest()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("bad request"));

		//Update 200
		requestBody = "{ \"title\": \"Test Movie 2\", \"description\": \"New Test Description\", \"runtimeMins\": 120 }";
		mockMvc.perform(put("/movies/1").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isCreated()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.title").value("Test Movie 2")).
				andExpect(jsonPath("$.data.rating").value("PG-13")).
				andExpect(jsonPath("$.data.description").value("New Test Description")).
				andExpect(jsonPath("$.data.runtimeMins").value(120));

		//Delete 404
		mockMvc.perform(delete("/movies/10")).
				andExpect(status().isNotFound()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Delete 200
		mockMvc.perform(delete("/movies/1").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.status").value("success")).andExpect(jsonPath("$.data.title").value("Test Movie 2")).andExpect(jsonPath("$.data.rating").value("PG-13")).andExpect(jsonPath("$.data.description").value("New Test Description")).andExpect(jsonPath("$.data.runtimeMins").value(120));

	}

	@Test
	public void testScreenings() throws Exception {
		String requestBody;
		//Create Movie
		requestBody = "{ \"title\": \"Test Movie\", \"rating\": \"PG-13\", \"description\": \"Test Description\", \"runtimeMins\": 120 }";
		MvcResult result = mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isCreated()).andReturn();
		String responseBody = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(responseBody);
		int dataId = responseJson.get("data").get("id").asInt();

		//Create 400
		requestBody = "{}";
		mockMvc.perform(post("/movies/" + dataId + "/screenings").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isBadRequest()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("bad request"));

		//Create 404
		requestBody = "{\n" + "  \"screenNumber\": 5,\n" + "  \"capacity\": 40,\n" + "  \"startsAt\": \"2023-03-19 11:30:00+00:00\"\n" + "}";
		mockMvc.perform(post("/movies/999/screenings").
						contentType(MediaType.APPLICATION_JSON).
						content(requestBody)).
				andExpect(status().isNotFound()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));


		//Create 200
		requestBody = "{\n" + "  \"screenNumber\": 5,\n" + "  \"capacity\": 40,\n" + "  \"startsAt\": \"2023-03-19 11:30:00+00:00\"\n" + "}";
		mockMvc.perform(post("/movies/" + dataId + "/screenings").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isCreated()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.screenNumber").value(5)).
				andExpect(jsonPath("$.data.capacity").value(40));

		//Get all 404
		mockMvc.perform(get("/movies/99/screenings")).
				andExpect(status().isNotFound()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Get all 200
		mockMvc.perform(get("/movies/" + dataId + "/screenings")).
				andExpect(status().isOk()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON)).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data[0].screenNumber").value(5)).
				andExpect(jsonPath("$.data[0].capacity").value(40));
	}

	@Test
	public void testTickets() throws Exception {
		String requestBody;
		int movie_id;
		int customer_id;
		int screening_id;

		//Create movie
		requestBody = "{ \"title\": \"Test Movie\", \"rating\": \"PG-13\", \"description\": \"Test Description\", \"runtimeMins\": 120 }";
		MvcResult result = mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isCreated()).andReturn();
		String responseBody = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(responseBody);
		movie_id = responseJson.get("data").get("id").asInt();

		//Create Screening
		requestBody = "{\n" +
				"  \"screenNumber\": 5,\n" +
				"  \"capacity\": 40,\n" +
				"  \"startsAt\": \"2023-03-19 11:30:00+00:00\"\n" +
				"}";
		mockMvc.perform(post("/movies/" + movie_id + "/screenings").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isCreated()).andReturn();
		responseBody = result.getResponse().getContentAsString();
		objectMapper = new ObjectMapper();
		responseJson = objectMapper.readTree(responseBody);
		screening_id = responseJson.get("data").get("id").asInt();

		//Create Customer
		requestBody = "{\n" +
				"  \"name\": \"Chris Wolstenholme\",\n" +
				"  \"email\": \"chris@muse.mu\",\n" +
				"  \"phone\": \"+44729388192\"\n" +
				"}";
		mockMvc.perform(post("/customers").
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isCreated()).andReturn();
		responseBody = result.getResponse().getContentAsString();
		objectMapper = new ObjectMapper();
		responseJson = objectMapper.readTree(responseBody);
		customer_id = responseJson.get("data").get("id").asInt();

		//Create 404
		requestBody = "{\n" +
				"  \"numSeats\": 3\n" +
				"}";
		mockMvc.perform(post("/customers/" + 99 + "/screenings/" + screening_id).
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isNotFound()).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));
		requestBody = "{\n" +
				"  \"numSeats\": 3\n" +
				"}";

		//Create 404
		mockMvc.perform(post("/customers/" + customer_id + "/screenings/" + 99).
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isNotFound()).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Create 200m
		requestBody = "{\n" +
				"  \"numSeats\": 3\n" +
				"}";
		mockMvc.perform(post("/customers/" + customer_id + "/screenings/" + screening_id).
						contentType(MediaType.APPLICATION_JSON).content(requestBody)).
				andExpect(status().isCreated()).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data.numSeats").value(3));

		//Get all 404
		mockMvc.perform(get("/customers/" + 99 + "/screenings/" + screening_id)).
				andExpect(status().isNotFound()).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Get all 404
		mockMvc.perform(get("/customers/" + customer_id + "/screenings/" + 99)).
				andExpect(status().isNotFound()).
				andExpect(jsonPath("$.status").value("error")).
				andExpect(jsonPath("$.data.message").value("not found"));

		//Get all 200
		mockMvc.perform(get("/customers/" + customer_id + "/screenings/" + screening_id)).
				andExpect(status().isOk()).
				andExpect(jsonPath("$.status").value("success")).
				andExpect(jsonPath("$.data[0].numSeats").value(3));
	}
}
