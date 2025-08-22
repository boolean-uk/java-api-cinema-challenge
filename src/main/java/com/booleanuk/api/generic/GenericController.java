package com.booleanuk.api.generic;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

abstract public class GenericController<Entity extends GenericEntity<Entity>> {
  protected final GenericRepository<Entity> repository;

  public GenericController(GenericRepository<Entity> repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<Entity>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<Entity> get(@PathVariable int id) throws ResponseStatusException {
    return this.repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No entity with id '" + id + "' was found"));
  }

  @PostMapping
  public ResponseEntity<Entity> post(@RequestBody Entity entity) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(entity));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Entity> put(@PathVariable int id, @RequestBody Entity entity) {
    return this.repository.findById(id).map(existing -> {
      existing.update(entity);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(existing));
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No entity with id '" + id + "' was found"));
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Entity> delete(@PathVariable int id) {
    var existing = this.repository.findById(id);
    if (existing.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No entity with id '" + id + "' was found");

    this.repository.deleteById(id);
    return ResponseEntity.ok(existing.get());
  }
}