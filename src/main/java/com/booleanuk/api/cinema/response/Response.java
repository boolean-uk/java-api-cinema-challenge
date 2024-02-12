package com.booleanuk.api.cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({"status", "data"})
public class Response {

    @JsonProperty
    private Object data;

    @JsonProperty
    private String status;

    public Response(Object data, String status) {
        this.data = data;
        this.status = status;
    }
}
