package com.cricket.scorer.service;

import com.cricket.scorer.model.Player;
import com.cricket.scorer.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player playerDetails) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        
        player.setName(playerDetails.getName());
        player.setTeamId(playerDetails.getTeamId());
        player.setRole(playerDetails.getRole());
        player.setJerseyNumber(playerDetails.getJerseyNumber());
        
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        playerRepository.delete(player);
    }

    public List<Player> getPlayersByTeamId(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public List<Player> getPlayersByRole(String role) {
        return playerRepository.findByRole(role);
    }

    public List<Player> searchPlayersByName(String name) {
        return playerRepository.findByNameContainingIgnoreCase(name);
    }
}
