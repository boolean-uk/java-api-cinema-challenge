package com.booleanuk.api.cinema.Dtos;

import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningDto {
    private int id;
    private int screenNumber;
    private Instant startsAt;
    private int capacity;
    private Instant createdAt;
    private Instant updatedAt;
}
