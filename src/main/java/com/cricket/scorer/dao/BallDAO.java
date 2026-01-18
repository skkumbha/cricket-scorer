package com.cricket.scorer.dao;

import com.cricket.scorer.model.Ball;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for Ball entity
 */
public class BallDAO extends AbstractDAO<Ball, Integer> {
    
    public BallDAO() {
        super(Ball.class);
    }
    
    /**
     * Find balls by over ID
     * @param overId Over ID
     * @return List of balls in the over
     */
    public List<Ball> findByOverId(Integer overId) {
        TypedQuery<Ball> query = entityManager.createQuery(
            "SELECT b FROM Ball b WHERE b.over.id = :overId ORDER BY b.ballNumber", Ball.class);
        query.setParameter("overId", overId);
        return query.getResultList();
    }
    
    /**
     * Find wicket balls in an innings
     * @param inningsId Innings ID
     * @return List of balls that resulted in wickets
     */
    public List<Ball> findWicketsByInningsId(Integer inningsId) {
        TypedQuery<Ball> query = entityManager.createQuery(
            "SELECT b FROM Ball b WHERE b.over.innings.id = :inningsId AND b.isWicket = true", Ball.class);
        query.setParameter("inningsId", inningsId);
        return query.getResultList();
    }
    
    /**
     * Find balls faced by a batsman
     * @param strikerId Striker ID
     * @return List of balls faced
     */
    public List<Ball> findByStrikerId(Integer strikerId) {
        TypedQuery<Ball> query = entityManager.createQuery(
            "SELECT b FROM Ball b WHERE b.striker.id = :strikerId", Ball.class);
        query.setParameter("strikerId", strikerId);
        return query.getResultList();
    }
    
    /**
     * Find balls bowled by a bowler
     * @param bowlerId Bowler ID
     * @return List of balls bowled
     */
    public List<Ball> findByBowlerId(Integer bowlerId) {
        TypedQuery<Ball> query = entityManager.createQuery(
            "SELECT b FROM Ball b WHERE b.bowler.id = :bowlerId", Ball.class);
        query.setParameter("bowlerId", bowlerId);
        return query.getResultList();
    }
}
