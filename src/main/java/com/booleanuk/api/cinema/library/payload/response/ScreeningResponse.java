package com.booleanuk.api.cinema.library.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
public class ScreeningResponse {
    private int id;
    private int screenNumber;
    private int capacity;
    private OffsetDateTime startsAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
