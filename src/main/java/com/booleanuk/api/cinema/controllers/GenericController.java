package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class GenericController<T, ID> {

    @Autowired
    private JpaRepository<T, ID> repository;
    private final Response<Object> response = new Response<>();

    @GetMapping
    public ResponseEntity<?> getAll() {
        response.setSuccess(repository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ID id) {
        T entity = repository.findById(id).orElse(null);
        if (entity == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.setSuccess(entity);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody T entity) {
        try {
            T newEntity = repository.save(entity);
            response.setSuccess(newEntity);
            return new ResponseEntity<>(newEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("Bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entity) {
        Optional<T> existingEntityOptional = repository.findById(id);
        if (existingEntityOptional.isEmpty()) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        T existingEntity = existingEntityOptional.get();

        BeanUtils.copyProperties(entity, existingEntity, "id", "createdAt", "updatedAt", "screenings");

        try {
            T updatedEntity = repository.save(existingEntity);
            response.setSuccess(updatedEntity);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("Bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        T entityToDelete = repository.findById(id).orElse(null);
        if (entityToDelete == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            repository.deleteById(id);
            response.setSuccess(entityToDelete);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setError("Bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
