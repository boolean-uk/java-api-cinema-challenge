package com.booleanuk.api.cinema.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String phone;
    @Builder.Default
    private final ZonedDateTime createdAt = ZonedDateTime.now();
    @Builder.Default
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}
