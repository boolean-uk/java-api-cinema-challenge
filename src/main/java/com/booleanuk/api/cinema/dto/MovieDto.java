package com.booleanuk.api.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class MovieDto {

    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime updatedAt;

    public MovieDto(int id, String title, String rating, String description, int runtimeMins,OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
