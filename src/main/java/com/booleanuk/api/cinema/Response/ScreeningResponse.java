package com.booleanuk.api.cinema.Response;

import com.booleanuk.api.cinema.Model.Screening;

public class ScreeningResponse {
    private String status;

    private Screening data;

    public ScreeningResponse(String status, Screening data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Screening getData() {
        return data;
    }

    public void setData(Screening data) {
        this.data = data;
    }
}
