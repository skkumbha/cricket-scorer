package com.cricket.scorer.repository;

import com.cricket.scorer.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByStatus(String status);
    List<Match> findByTeam1IdOrTeam2Id(Long team1Id, Long team2Id);
}
