package com.booleanuk.api.cinema.library.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class ScreeningRequest {
    private int screenNumber;
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX") // pattern voor input
    private OffsetDateTime startsAt;
}