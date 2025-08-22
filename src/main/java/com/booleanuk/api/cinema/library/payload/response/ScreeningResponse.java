package com.booleanuk.api.cinema.library.payload.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScreeningResponse {
    private int id;
    private int screenNumber;
    private int capacity;
    private LocalDateTime startsAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
