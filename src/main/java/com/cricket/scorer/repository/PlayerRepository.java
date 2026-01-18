package com.cricket.scorer.repository;

import com.cricket.scorer.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByRole(String role);
    List<Player> findByFullNameContainingIgnoreCase(String fullName);
}
