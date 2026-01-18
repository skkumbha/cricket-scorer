package com.cricket.scorer.repository;

import com.cricket.scorer.model.MatchTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchTeamRepository extends JpaRepository<MatchTeam, Long> {
    List<MatchTeam> findByMatchId(Long matchId);
    List<MatchTeam> findByTeamId(Long teamId);
}
