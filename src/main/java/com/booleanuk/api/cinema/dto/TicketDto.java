package com.booleanuk.api.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TicketDto {
    private int id;
    private int numSeats;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime createdAt;
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime updatedAt;

    public TicketDto(int id, int numSeats, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.numSeats = numSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
