package com.booleanuk.api.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ScreeningDto {
    private int id;
    private int screenNumber;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime startsAt;
    private int capacity;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime updatedAt;

    public ScreeningDto(int id, int screenNumber, int capacity, OffsetDateTime startsAt, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
