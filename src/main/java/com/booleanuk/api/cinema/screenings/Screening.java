package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class Screening implements Serializable, GenericEntity<Screening> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(nullable = false)
  int screenNumber;

  @Column(nullable = false)
  int capacity;

  @Column(nullable = false)
  String startsAt;

  @Override
  public void update(Screening source) {
    this.id = source.id;
    this.screenNumber = source.screenNumber;
    this.capacity = source.capacity;
    this.startsAt = source.startsAt;
  }
}