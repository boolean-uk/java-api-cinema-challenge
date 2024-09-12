package com.booleanuk.api.cinema.customers.tickets;

import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.movies.screenings.Screening;
import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class Ticket implements GenericEntity<Ticket> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "screening_id")
  private Screening screening;

  @Column(nullable = false)
  private int numSeats;

  @Override
  public void update(Ticket source) {
  }
}