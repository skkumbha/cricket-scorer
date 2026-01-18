package com.cricket.scorer.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic DAO interface for CRUD operations
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public interface GenericDAO<T, ID> {
    
    /**
     * Save or update an entity
     * @param entity Entity to save
     * @return Saved entity
     */
    T save(T entity);
    
    /**
     * Find entity by ID
     * @param id Primary key
     * @return Optional containing entity if found
     */
    Optional<T> findById(ID id);
    
    /**
     * Find all entities
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Delete entity
     * @param entity Entity to delete
     */
    void delete(T entity);
    
    /**
     * Delete entity by ID
     * @param id Primary key
     */
    void deleteById(ID id);
    
    /**
     * Check if entity exists by ID
     * @param id Primary key
     * @return true if exists
     */
    boolean existsById(ID id);
    
    /**
     * Count all entities
     * @return Number of entities
     */
    long count();
}
