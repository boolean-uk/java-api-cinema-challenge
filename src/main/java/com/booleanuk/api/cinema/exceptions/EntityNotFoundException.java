package com.booleanuk.api.cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass, int id) {
        super("Entity " + entityClass.getSimpleName() + " with id " + id + " does not exist!");
    }
}
