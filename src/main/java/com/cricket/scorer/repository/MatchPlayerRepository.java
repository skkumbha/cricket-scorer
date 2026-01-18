package com.cricket.scorer.repository;

import com.cricket.scorer.model.MatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {
    List<MatchPlayer> findByMatchId(Long matchId);
    List<MatchPlayer> findByPlayerId(Long playerId);
    List<MatchPlayer> findByMatchIdAndTeamId(Long matchId, Long teamId);
}
