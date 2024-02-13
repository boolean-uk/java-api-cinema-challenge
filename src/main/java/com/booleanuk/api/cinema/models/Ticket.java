package com.booleanuk.api.cinema.models;

import com.booleanuk.api.cinema.controllers.TicketController;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer numSeats;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "email", "phone", "createdAt", "updatedAt" })
    private Customer customer_id;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "screenNumber", "capacity", "startsAt", "movie_id" })
    private Screening screening_id;

    @Override
    public boolean haveNullFields() {
        return numSeats == null;
    }

    @Override
    public void copyOverData(Model model) {
        try {
            Ticket _other = (Ticket) model;

            numSeats = _other.numSeats;
            customer_id = _other.customer_id;
            screening_id = _other.screening_id;
        }
        catch (Exception e) {}
    }
}
