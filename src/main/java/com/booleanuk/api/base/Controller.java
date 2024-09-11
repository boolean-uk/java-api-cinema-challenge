package com.booleanuk.api.base;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

abstract public class Controller<Entity extends Patchable<Entity>> {
  private final JpaRepository<Entity, Integer> repository;

  protected Controller(JpaRepository<Entity, Integer> repository) {
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
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create entity " + e.getMessage());
    }
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Entity> put(@PathVariable int id, @RequestBody Entity entity) {
    var existing = this.repository.findById(id);
    if (existing.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No entity with id '" + id + "' was found");

    existing.get().patch(entity);
    return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(existing.get()));
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