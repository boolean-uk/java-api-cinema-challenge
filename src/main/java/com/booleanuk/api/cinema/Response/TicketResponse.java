package com.booleanuk.api.cinema.Response;

import com.booleanuk.api.cinema.Model.Ticket;

public class TicketResponse {
    private String status;

    private Ticket data;

    public TicketResponse(String status, Ticket data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ticket getData() {
        return data;
    }

    public void setData(Ticket data) {
        this.data = data;
    }
}
