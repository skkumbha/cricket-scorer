package com.cricket.scorer.repository;

import com.cricket.scorer.model.Ball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallRepository extends JpaRepository<Ball, Long> {
    List<Ball> findByOverId(Long overId);
    List<Ball> findByInningsId(Long inningsId);
    List<Ball> findByOverIdOrderByBallNumber(Long overId);
}
