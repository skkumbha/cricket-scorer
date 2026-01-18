package com.cricket.scorer.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Abstract base DAO implementation providing common CRUD operations
 * @param <T> Entity type
 * @param <ID> Primary key type
 */
public abstract class AbstractDAO<T, ID> implements GenericDAO<T, ID> {
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    private final Class<T> entityClass;
    
    protected AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    public T save(T entity) {
        if (entityManager.contains(entity)) {
            return entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
            return entity;
        }
    }
    
    @Override
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }
    
    @Override
    public List<T> findAll() {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query = entityManager.createQuery(queryString, entityClass);
        return query.getResultList();
    }
    
    @Override
    public void delete(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }
    
    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }
    
    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }
    
    @Override
    public long count() {
        String queryString = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        return query.getSingleResult();
    }
    
    /**
     * Get the EntityManager
     * @return EntityManager instance
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    /**
     * Set the EntityManager (for testing purposes)
     * @param entityManager EntityManager instance
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
