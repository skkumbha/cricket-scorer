package com.cricket.scorer.repository;

import com.cricket.scorer.model.PlayerScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {
        PlayerScore findByPlayerIdAndInningsId(Long playerId, Long inningsId);
}
