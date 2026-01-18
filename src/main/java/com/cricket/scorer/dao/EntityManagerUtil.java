package com.cricket.scorer.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility class for managing EntityManager instances
 */
public class EntityManagerUtil {
    
    private static final String PERSISTENCE_UNIT_NAME = "cricket-scorer-pu";
    private static EntityManagerFactory entityManagerFactory;
    
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to initialize EntityManagerFactory: " + e.getMessage());
        }
    }
    
    /**
     * Get EntityManager instance
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
    /**
     * Close EntityManagerFactory
     */
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
    
    /**
     * Check if EntityManagerFactory is open
     * @return true if open
     */
    public static boolean isOpen() {
        return entityManagerFactory != null && entityManagerFactory.isOpen();
    }
}
