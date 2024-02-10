package com.booleanuk.api.cinema.response;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
// Use TicketController to test how this ResponseStuff works
// Using Generic Class such that I can specify different controller classes to be used
// ***Refer to @GetMapping("tickets/responsetest") in TicketController!!***

public class ApiResponse<T> {
    private String status;
    private Object data;

    public void setData(T data) {
        this.data = data;
    }

    public void setData(List<T> dataList) {
        this.data = dataList;
    }

    public List<T> asList(Collection<T> elements) {
        return new ArrayList<>(elements);
    }

}
