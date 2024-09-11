package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer implements GenericEntity<Customer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phone;

  @Override
  public void update(Customer other) {
    this.name = other.name;
    this.email = other.email;
    this.phone = other.phone;
  }
}