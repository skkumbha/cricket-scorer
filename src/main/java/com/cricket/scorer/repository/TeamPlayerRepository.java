package com.cricket.scorer.repository;

import com.cricket.scorer.model.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
    List<TeamPlayer> findByTeamId(Long teamId);
    List<TeamPlayer> findByPlayerId(Long playerId);
}
