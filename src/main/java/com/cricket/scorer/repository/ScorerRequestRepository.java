package com.cricket.scorer.repository;

import com.cricket.scorer.model.ScorerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScorerRequestRepository extends JpaRepository<ScorerRequest, Long> {
    List<ScorerRequest> findByMatchId(Long matchId);
    List<ScorerRequest> findByUserId(String userId);
    List<ScorerRequest> findByRequestStatus(String requestStatus);
}
