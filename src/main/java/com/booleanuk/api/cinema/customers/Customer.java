package com.booleanuk.api.cinema.customers;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Serializable, GenericEntity<Customer> {
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
  public void update(Customer source) {
    this.id = source.id;
    this.name = source.name;
    this.email = source.email;
    this.phone = source.phone;
  }
}