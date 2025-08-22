package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.models.Ticket;

public class TicketDto {
    private int customerId;
    private String name;
    private int screeningId;
    private String title;
    private int numSeats;

    public TicketDto(Ticket ticket) {
        this.customerId = ticket.getCustomer().getId();
        this.name = ticket.getCustomer().getName();
        this.screeningId = ticket.getScreening().getId();
        this.title = ticket.getScreening().getMovie().getTitle();
        this.numSeats = ticket.getNumSeats();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(int screeningId) {
        this.screeningId = screeningId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }
}
