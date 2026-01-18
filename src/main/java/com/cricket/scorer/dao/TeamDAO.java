package com.cricket.scorer.dao;

import com.cricket.scorer.model.Team;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for Team entity
 */
public class TeamDAO extends AbstractDAO<Team, Integer> {
    
    public TeamDAO() {
        super(Team.class);
    }
    
    /**
     * Find teams by name (partial match)
     * @param name Team name to search
     * @return List of matching teams
     */
    public List<Team> findByNameContaining(String name) {
        TypedQuery<Team> query = entityManager.createQuery(
            "SELECT t FROM Team t WHERE LOWER(t.name) LIKE LOWER(:name)", Team.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
    
    /**
     * Find team by exact name
     * @param name Team name
     * @return List of teams with exact name
     */
    public List<Team> findByName(String name) {
        TypedQuery<Team> query = entityManager.createQuery(
            "SELECT t FROM Team t WHERE t.name = :name", Team.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    /**
     * Find team by short name
     * @param shortName Team short name
     * @return List of teams with matching short name
     */
    public List<Team> findByShortName(String shortName) {
        TypedQuery<Team> query = entityManager.createQuery(
            "SELECT t FROM Team t WHERE t.shortName = :shortName", Team.class);
        query.setParameter("shortName", shortName);
        return query.getResultList();
    }
}
