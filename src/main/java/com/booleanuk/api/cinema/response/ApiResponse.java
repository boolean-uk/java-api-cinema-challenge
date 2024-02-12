package com.booleanuk.api.cinema.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
// Use TicketController to test how this ResponseStuff works
// Using Generic Class such that I can specify different controller classes to be used
// ***Refer to @GetMapping("tickets/responsetest") in TicketController!!***

public class ApiResponse<T> {

    private String status;
    private Object data;

    public ApiResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setData(List<T> dataList) {
        this.data = dataList;
    }

    public void setData(Message message) {
        this.data = message;
    }

    public List<T> asList(Collection<T> elements) {
        return new ArrayList<>(elements);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }

        // Getter and setter for message
    }

}


