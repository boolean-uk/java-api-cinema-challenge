package com.booleanuk.api.cinema.models;

import com.booleanuk.api.cinema.controllers.AuthController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "customers",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(groups = AuthController.class)
    @Size(max = 120)
    @JsonIgnore
    private String password;

    @NotBlank
    private String phone;

    private OffsetDateTime created;

    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties({"customer"})
    List<Ticket> tickets;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
