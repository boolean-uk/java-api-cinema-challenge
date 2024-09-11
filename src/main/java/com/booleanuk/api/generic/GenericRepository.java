package com.booleanuk.api.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<Entity extends GenericEntity<Entity>> extends JpaRepository<Entity, Integer> {
}