package com.booleanuk.api.cinema.library.payload.request;

import lombok.Data;

@Data
public class MovieRequest {
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
}
