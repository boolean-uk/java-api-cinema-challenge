package com.booleanuk.api.cinema;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
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

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
public class ScreeningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testGetAllScreenings() throws Exception {
        screeningRepository.deleteAll();
        Movie m1 = movieRepository.save(new Movie("Dracula", "PG-13", "vampires", 120));

        OffsetDateTime t1 = OffsetDateTime.now();

        Screening s1 = new Screening(5, 545, t1);
        s1.setMovie(m1);
        screeningRepository.save(s1);

        s1.setStartsAt( "2023-03-19 11:30:00+00:00");
        OffsetDateTime t2 = OffsetDateTime.now();

        Screening s2 = new Screening(7, 565, t2);
        s2.setMovie(m1);
        screeningRepository.save(s2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");


        mockMvc.perform(get("/movies/{id}/screenings", m1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].screenNumber").value(5))
                .andExpect(jsonPath("$.data[0].capacity").value(545))
                .andExpect(jsonPath("$.data[0].startsAt").value(t1.format(formatter)))
                .andExpect(jsonPath("$.data[1].screenNumber").value(7))
                .andExpect(jsonPath("$.data[1].capacity").value(565))
                .andExpect(jsonPath("$.data[1].startsAt").value(t2.format(formatter)))
                .andExpect(jsonPath("$.data[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.data[1].id").value(s2.getId()));

        mockMvc.perform(get("/movies/{id}/screenings", 77777777))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data.message").value("not found"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

//    @Test
//    public void testCreateAScreening() throws Exception {
//        OffsetDateTime t1 = OffsetDateTime.now();
//        Screening validScreening = new Screening(5, 545, t1);
//        OffsetDateTime t2 = OffsetDateTime.now();
//        Screening invalidScreening = new Screening(7, 565, t2);
//
//        invalidScreening.setStartsAt(null);
//
//        String validRequestBody = objectMapper.writeValueAsString(validScreening);
//        String invalidRequestBody = objectMapper.writeValueAsString(invalidScreening);
//
//        mockMvc.perform(post("/screenings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(validRequestBody))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data.name").value("Jane Doe"))
//                .andExpect(jsonPath("$.status").value("success"));
//
//        mockMvc.perform(post("/screenings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidRequestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value("error"))
//                .andExpect(jsonPath("$.data.message").value("bad request"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    public void testUpdateScreening() throws Exception {
//        Screening screening = screeningRepository.save(new Screening("Jane Doe", "jane@doe.com", "123456789"));
//
//        HashMap<String, String> update = new HashMap<>();
//        update.put("name", "Dracula");
//        update.put("email", "dracula@gmail.com");
//
//        String requestBody = objectMapper.writeValueAsString(update);
//
//        mockMvc.perform(put("/screenings/{id}", screening.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.data.name").value("Dracula"))
//                .andExpect(jsonPath("$.data.email").value("dracula@gmail.com"))
//                .andExpect(jsonPath("$.data.phone").value("123456789"));
//
//        screeningRepository.delete(screening);
//
//        mockMvc.perform(put("/screenings/{id}", screening.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value("error"))
//                .andExpect(jsonPath("$.data.message").value("not found"));
//    }
//
//    @Test
//    public void testDeleteScreening() throws Exception {
//        Screening screening = screeningRepository.save(new Screening("Jane Doe", "jane@doe.com", "123456789"));
//
//        mockMvc.perform(delete("/screenings/{id}", screening.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value("success"))
//                .andExpect(jsonPath("$.data.name").value("Jane Doe"))
//                .andExpect(jsonPath("$.data.email").value("jane@doe.com"))
//                .andExpect(jsonPath("$.data.phone").value("123456789"));
//
//        mockMvc.perform(delete("/screenings/{id}", screening.getId()))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value("error"))
//                .andExpect(jsonPath("$.data.message").value("not found"));
//    }
//

}
