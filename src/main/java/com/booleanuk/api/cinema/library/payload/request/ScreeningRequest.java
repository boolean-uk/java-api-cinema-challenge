package com.booleanuk.api.cinema.library.payload.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScreeningRequest {
    private int screenNumber;
    private int capacity;
    private LocalDateTime startsAt;
}