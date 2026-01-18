package com.cricket.scorer.dao;

import com.cricket.scorer.model.Over;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for Over entity
 */
public class OverDAO extends AbstractDAO<Over, Integer> {
    
    public OverDAO() {
        super(Over.class);
    }
    
    /**
     * Find overs by innings ID
     * @param inningsId Innings ID
     * @return List of overs in the innings
     */
    public List<Over> findByInningsId(Integer inningsId) {
        TypedQuery<Over> query = entityManager.createQuery(
            "SELECT o FROM Over o WHERE o.innings.id = :inningsId ORDER BY o.overNumber", Over.class);
        query.setParameter("inningsId", inningsId);
        return query.getResultList();
    }
    
    /**
     * Find overs bowled by a specific bowler
     * @param bowlerId Bowler ID
     * @return List of overs bowled by the player
     */
    public List<Over> findByBowlerId(Integer bowlerId) {
        TypedQuery<Over> query = entityManager.createQuery(
            "SELECT o FROM Over o WHERE o.bowler.id = :bowlerId", Over.class);
        query.setParameter("bowlerId", bowlerId);
        return query.getResultList();
    }
    
    /**
     * Find overs bowled by a bowler in a specific innings
     * @param bowlerId Bowler ID
     * @param inningsId Innings ID
     * @return List of overs
     */
    public List<Over> findByBowlerIdAndInningsId(Integer bowlerId, Integer inningsId) {
        TypedQuery<Over> query = entityManager.createQuery(
            "SELECT o FROM Over o WHERE o.bowler.id = :bowlerId AND o.innings.id = :inningsId ORDER BY o.overNumber", 
            Over.class);
        query.setParameter("bowlerId", bowlerId);
        query.setParameter("inningsId", inningsId);
        return query.getResultList();
    }
}
