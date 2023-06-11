package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("response")
public class CustomResponse {
    @JsonProperty("status")
    private final String status;
    @JsonProperty("data")
    private final Object data;

    public CustomResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
