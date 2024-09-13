package com.booleanuk.api.cinema.DTO;

import java.time.LocalDateTime;

public class TicketDTO {
    private Integer id;
    private int numSeats;
    private LocalDateTime startsAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer customerId;
    private Integer screeningId;

    public TicketDTO() {
    }

    public TicketDTO(Integer id, int numSeats, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt, Integer customerId, Integer screeningId) {
        this.id = id;
        this.numSeats = numSeats;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerId = customerId;
        this.screeningId = screeningId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Integer screeningId) {
        this.screeningId = screeningId;
    }
}
