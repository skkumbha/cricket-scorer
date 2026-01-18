package com.cricket.scorer.repository;

import com.cricket.scorer.model.MatchAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchAwardRepository extends JpaRepository<MatchAward, Long> {
    List<MatchAward> findByMatchId(Long matchId);
    List<MatchAward> findByPlayerId(Long playerId);
}
