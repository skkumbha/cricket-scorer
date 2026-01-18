package com.cricket.scorer.repository;

import com.cricket.scorer.model.MatchAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchAccessRepository extends JpaRepository<MatchAccess, Long> {
    List<MatchAccess> findByMatchId(Long matchId);
    List<MatchAccess> findByUserId(String userId);
    Optional<MatchAccess> findByMatchIdAndUserId(Long matchId, String userId);
}
