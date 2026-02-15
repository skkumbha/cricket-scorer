package com.cricket.scorer.service;

import com.cricket.scorer.dto.PlayerDTO;
import com.cricket.scorer.mapper.PlayerMapper;
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
    @Autowired
    private PlayerMapper playerMapper;

    public Optional<PlayerDTO> getPlayerById(Long id) {
        return playerRepository.findById(id).map(playerMapper::toDto);
    }

    public Optional<Player> getPlayerEntityById(Long id) {
        return playerRepository.findById(id);
    }

    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDto(savedPlayer);
    }

    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        
        if (playerDTO.getFullName() != null) {
            player.setFullName(playerDTO.getFullName());
        }
        if (playerDTO.getRole() != null) {
            player.setRole(playerDTO.getRole());
        }
        if (playerDTO.getJerseyNumber() != null) {
            player.setJerseyNumber(playerDTO.getJerseyNumber());
        }
        
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDto(savedPlayer);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        playerRepository.delete(player);
    }

    public List<PlayerDTO> getPlayersByTeamId(Long teamId) {
        // This method no longer works with the new schema
        // Players are linked to teams through team_players table
        return List.of();
    }

    public List<PlayerDTO> getPlayersByRole(String role) {
        return playerMapper.toDtoList(playerRepository.findByRole(role));
    }

    public List<PlayerDTO> searchPlayersByName(String name) {
        return playerMapper.toDtoList(playerRepository.findByFullNameContainingIgnoreCase(name));
    }

    public Player toEntity(PlayerDTO playerDTO) {
        return playerMapper.toEntity(playerDTO);
    }
}
