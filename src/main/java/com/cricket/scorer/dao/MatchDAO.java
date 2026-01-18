package com.cricket.scorer.dao;

import com.cricket.scorer.model.Match;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DAO for Match entity
 */
public class MatchDAO extends AbstractDAO<Match, UUID> {
    
    public MatchDAO() {
        super(Match.class);
    }
    
    /**
     * Find matches by status
     * @param status Match status (e.g., "setup", "in_progress", "completed")
     * @return List of matches with matching status
     */
    public List<Match> findByStatus(String status) {
        TypedQuery<Match> query = entityManager.createQuery(
            "SELECT m FROM Match m WHERE m.status = :status", Match.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    /**
     * Find matches by type
     * @param matchType Match type (e.g., "Friendly", "Tournament")
     * @return List of matches with matching type
     */
    public List<Match> findByMatchType(String matchType) {
        TypedQuery<Match> query = entityManager.createQuery(
            "SELECT m FROM Match m WHERE m.matchType = :matchType", Match.class);
        query.setParameter("matchType", matchType);
        return query.getResultList();
    }
    
    /**
     * Find matches by location
     * @param location Match location
     * @return List of matches at the location
     */
    public List<Match> findByLocation(String location) {
        TypedQuery<Match> query = entityManager.createQuery(
            "SELECT m FROM Match m WHERE m.location = :location", Match.class);
        query.setParameter("location", location);
        return query.getResultList();
    }
    
    /**
     * Find matches within a date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of matches within the date range
     */
    public List<Match> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<Match> query = entityManager.createQuery(
            "SELECT m FROM Match m WHERE m.startTime BETWEEN :startDate AND :endDate", Match.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    
    /**
     * Find matches involving a specific team
     * @param teamId Team ID
     * @return List of matches involving the team
     */
    public List<Match> findByTeamId(Integer teamId) {
        TypedQuery<Match> query = entityManager.createQuery(
            "SELECT DISTINCT m FROM Match m JOIN m.matchTeams mt WHERE mt.team.id = :teamId", Match.class);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }
    
    /**
     * Find recent matches
     * @param limit Maximum number of matches to return
     * @return List of recent matches
     */
    public List<Match> findRecentMatches(int limit) {
        TypedQuery<Match> query = entityManager.createQuery(
            "SELECT m FROM Match m ORDER BY m.startTime DESC", Match.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
