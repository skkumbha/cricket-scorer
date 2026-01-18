package com.cricket.scorer.dao;

import com.cricket.scorer.model.Player;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO for Player entity
 */
public class PlayerDAO extends AbstractDAO<Player, Integer> {
    
    public PlayerDAO() {
        super(Player.class);
    }
    
    /**
     * Find players by name (partial match)
     * @param name Player name to search
     * @return List of matching players
     */
    public List<Player> findByNameContaining(String name) {
        TypedQuery<Player> query = entityManager.createQuery(
            "SELECT p FROM Player p WHERE LOWER(p.fullName) LIKE LOWER(:name)", Player.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
    
    /**
     * Find players by role
     * @param role Player role (e.g., "Batsman", "Bowler", "All-rounder")
     * @return List of players with matching role
     */
    public List<Player> findByRole(String role) {
        TypedQuery<Player> query = entityManager.createQuery(
            "SELECT p FROM Player p WHERE p.role = :role", Player.class);
        query.setParameter("role", role);
        return query.getResultList();
    }
    
    /**
     * Find player by jersey number and team
     * @param jerseyNumber Jersey number
     * @param teamId Team ID
     * @return List of players matching criteria
     */
    public List<Player> findByJerseyNumberAndTeam(Integer jerseyNumber, Integer teamId) {
        TypedQuery<Player> query = entityManager.createQuery(
            "SELECT p FROM Player p JOIN p.teamPlayers tp WHERE p.jerseyNumber = :jerseyNumber AND tp.team.id = :teamId", 
            Player.class);
        query.setParameter("jerseyNumber", jerseyNumber);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }
    
    /**
     * Find all players in a team
     * @param teamId Team ID
     * @return List of players in the team
     */
    public List<Player> findByTeamId(Integer teamId) {
        TypedQuery<Player> query = entityManager.createQuery(
            "SELECT p FROM Player p JOIN p.teamPlayers tp WHERE tp.team.id = :teamId", Player.class);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }
}
