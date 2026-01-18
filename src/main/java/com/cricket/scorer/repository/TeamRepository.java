package com.cricket.scorer.repository;

import com.cricket.scorer.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByCountry(String country);
    List<Team> findByNameContainingIgnoreCase(String name);
}
