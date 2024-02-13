package com.booleanuk.api.cinema.Response;

import java.util.List;

public class Responses {
    private String status;

    private List<Object> data;

    public Responses(String status, List<Object> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
