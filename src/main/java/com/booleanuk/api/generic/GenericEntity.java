package com.booleanuk.api.generic;

public interface GenericEntity<Entity> {
  void update(Entity source);
}