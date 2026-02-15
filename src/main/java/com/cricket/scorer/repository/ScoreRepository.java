package com.cricket.scorer.repository;

import com.cricket.scorer.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    public Optional<Score> findByInningsId(Long inningsId);
}
