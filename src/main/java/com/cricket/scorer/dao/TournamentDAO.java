package com.cricket.scorer.dao;

import com.cricket.scorer.model.Tournament;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 * DAO for Tournament entity
 */
public class TournamentDAO extends AbstractDAO<Tournament, Integer> {
    
    public TournamentDAO() {
        super(Tournament.class);
    }
    
    /**
     * Find tournaments by name (partial match)
     * @param name Tournament name to search
     * @return List of matching tournaments
     */
    public List<Tournament> findByNameContaining(String name) {
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT t FROM Tournament t WHERE LOWER(t.name) LIKE LOWER(:name)", Tournament.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
    
    /**
     * Find tournaments by season
     * @param season Season (e.g., "2024", "2024-25")
     * @return List of tournaments in the season
     */
    public List<Tournament> findBySeason(String season) {
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT t FROM Tournament t WHERE t.season = :season", Tournament.class);
        query.setParameter("season", season);
        return query.getResultList();
    }
    
    /**
     * Find active tournaments (currently ongoing)
     * @param currentDate Current date
     * @return List of active tournaments
     */
    public List<Tournament> findActiveTournaments(LocalDate currentDate) {
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT t FROM Tournament t WHERE t.startDate <= :currentDate AND t.endDate >= :currentDate", 
            Tournament.class);
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }
    
    /**
     * Find upcoming tournaments
     * @param currentDate Current date
     * @return List of upcoming tournaments
     */
    public List<Tournament> findUpcomingTournaments(LocalDate currentDate) {
        TypedQuery<Tournament> query = entityManager.createQuery(
            "SELECT t FROM Tournament t WHERE t.startDate > :currentDate ORDER BY t.startDate", 
            Tournament.class);
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }
}
