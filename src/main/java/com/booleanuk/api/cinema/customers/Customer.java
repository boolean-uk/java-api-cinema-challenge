package com.booleanuk.api.cinema.customers;


import com.booleanuk.api.base.Patchable;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer implements Patchable<Customer> {
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
  public void patch(Customer other) {
    this.name = other.name;
    this.email = other.email;
    this.phone = other.phone;
  }
}