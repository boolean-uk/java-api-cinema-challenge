package com.booleanuk.api.cinema.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MovieJsonTest {

    @Autowired
    private JacksonTester<Movie> json;

    private static Movie movie;
    @BeforeAll
    public static void setup() {
        movie = new Movie(
                "Inception",
                "8.8",
                "A thief who steals corporate secrets through the use of dream-sharing technology is given the " +
                        "inverse task of planting an idea into the mind of a C.E.O.",
                148,
                LocalDateTime.of(2022, 3, 15, 10, 30)
        );
    }
    @Test
    public void MovieSerializationTest() throws IOException {
        assertThat(true).isEqualTo(true);

        JsonContent<Movie> serializedMovie = json.write(movie);
        assertThat(serializedMovie).isStrictlyEqualToJson("expected.json");

        assertThat(serializedMovie).hasJsonPathNumberValue("@.id");
        assertThat(serializedMovie).hasJsonPathStringValue("@.title");
        assertThat(serializedMovie).hasJsonPathStringValue("@.rating");
        assertThat(serializedMovie).hasJsonPathStringValue("@.description");
        assertThat(serializedMovie).hasJsonPathNumberValue("@.runtimeMins");
        assertThat(serializedMovie).hasJsonPathStringValue("@.createdAt");
        assertThat(serializedMovie).hasJsonPathStringValue("@.updatedAt");

        assertThat(serializedMovie).extractingJsonPathNumberValue("@.id").isEqualTo(0);
        assertThat(serializedMovie).extractingJsonPathStringValue("@.title").isEqualTo("Inception");
        assertThat(serializedMovie).extractingJsonPathStringValue("@.rating").isEqualTo("8.8");
        assertThat(serializedMovie).extractingJsonPathStringValue("@.description")
                .isEqualTo("A thief who steals corporate secrets through the use of dream-sharing technology" +
                        " is given the inverse task of planting an idea into the mind of a C.E.O.");

        assertThat(serializedMovie).extractingJsonPathNumberValue("@.runtimeMins").isEqualTo(148);
        // TODO: check date's serialization, change date format everywhere
//        assertThat(serializedMovie).hasJsonPathStringValue("@.createdAt").isisEqualTo("2022-03-15T10:30:00");
//        assertThat(serializedMovie).hasJsonPathStringValue("@.updatedAt").isEqualTo("2022-03-15T10:30:00");
    }

    @Test
    public void MovieDeserializationTest() throws IOException {
        String expected = """
                {
                  "id": 0,
                  "title": "Inception",
                  "rating": "8.8",
                  "description": "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                  "runtimeMins": 148,
                  "createdAt": "2022-03-15T10:30:00",
                  "updatedAt": "2022-03-15T10:30:00"
                }
                """;
        assertThat(json.parse(expected)).isEqualTo(movie);
        assertThat(json.parseObject(expected).getId()).isEqualTo(0);
        assertThat(json.parseObject(expected).getTitle()).isEqualTo("Inception");
        assertThat(json.parseObject(expected).getRating()).isEqualTo("8.8");
        assertThat(json.parseObject(expected).getDescription()).isEqualTo("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.");
        assertThat(json.parseObject(expected).getRuntimeMins()).isEqualTo(148);
        assertThat(json.parseObject(expected).getCreatedAt()).isEqualTo("2022-03-15T10:30:00");
        assertThat(json.parseObject(expected).getUpdatedAt()).isEqualTo("2022-03-15T10:30:00");
    }
}
