package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ControllerTemplate<M extends Model> {
    @Autowired
    protected JpaRepository<M, Integer> repository;

    @GetMapping
    public List<M> getAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<M> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<M> create(@RequestBody M request) {
        if (request.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(repository.save(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<M> update(@PathVariable final Integer id, @RequestBody final M model) {
        if (model.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        M _targetModel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));
        _targetModel.copyOverData(model);
        return new ResponseEntity<>(repository.save(_targetModel), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<M> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
