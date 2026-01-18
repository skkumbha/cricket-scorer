package com.cricket.scorer.dao;

import com.cricket.scorer.model.Innings;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

/**
 * DAO for Innings entity
 */
public class InningsDAO extends AbstractDAO<Innings, Integer> {
    
    public InningsDAO() {
        super(Innings.class);
    }
    
    /**
     * Find innings by match ID
     * @param matchId Match ID
     * @return List of innings in the match
     */
    public List<Innings> findByMatchId(UUID matchId) {
        TypedQuery<Innings> query = entityManager.createQuery(
            "SELECT i FROM Innings i WHERE i.match.id = :matchId ORDER BY i.inningsNumber", Innings.class);
        query.setParameter("matchId", matchId);
        return query.getResultList();
    }
    
    /**
     * Find innings by batting team
     * @param teamId Team ID
     * @return List of innings where team was batting
     */
    public List<Innings> findByBattingTeamId(Integer teamId) {
        TypedQuery<Innings> query = entityManager.createQuery(
            "SELECT i FROM Innings i WHERE i.battingTeam.id = :teamId", Innings.class);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }
    
    /**
     * Find innings by bowling team
     * @param teamId Team ID
     * @return List of innings where team was bowling
     */
    public List<Innings> findByBowlingTeamId(Integer teamId) {
        TypedQuery<Innings> query = entityManager.createQuery(
            "SELECT i FROM Innings i WHERE i.bowlingTeam.id = :teamId", Innings.class);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }
    
    /**
     * Find a specific innings by match and innings number
     * @param matchId Match ID
     * @param inningsNumber Innings number
     * @return Innings if found
     */
    public Innings findByMatchIdAndInningsNumber(UUID matchId, Integer inningsNumber) {
        TypedQuery<Innings> query = entityManager.createQuery(
            "SELECT i FROM Innings i WHERE i.match.id = :matchId AND i.inningsNumber = :inningsNumber", 
            Innings.class);
        query.setParameter("matchId", matchId);
        query.setParameter("inningsNumber", inningsNumber);
        List<Innings> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
