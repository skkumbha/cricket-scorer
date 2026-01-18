package com.cricket.scorer.repository;

import com.cricket.scorer.model.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long> {
    List<Fixture> findByTournamentId(Long tournamentId);
    List<Fixture> findByMatchId(Long matchId);
}
