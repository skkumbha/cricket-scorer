package com.cricket.scorer.service;

import com.cricket.scorer.dto.PlayerDTO;
import com.cricket.scorer.dto.TeamDTO;
import com.cricket.scorer.mapper.TeamMapper;
import com.cricket.scorer.model.Player;
import com.cricket.scorer.model.Team;
import com.cricket.scorer.model.TeamPlayer;
import com.cricket.scorer.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamMapper teamMapper;

    public List<TeamDTO> getAllTeams() {
        return teamMapper.toDtoList(teamRepository.findAll());
    }

    public Optional<TeamDTO> getTeamById(Long id) {
        return teamRepository.findById(id).map(teamMapper::toDto);
    }

    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDto(savedTeam);
    }

    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        
        // Update only non-null fields from DTO
        if (teamDTO.getName() != null) {
            team.setName(teamDTO.getName());
        }
        if (teamDTO.getShortName() != null) {
            team.setShortName(teamDTO.getShortName());
        }
        if (teamDTO.getLogoUrl() != null) {
            team.setLogoUrl(teamDTO.getLogoUrl());
        }
        if (teamDTO.getPlayers()!= null) {
            for (var playerDTO : teamDTO.getPlayers()) {
                Optional<PlayerDTO> playerOpt = playerService.getPlayerById(playerDTO.getId());
                if (playerOpt.isEmpty()) {
                    throw new RuntimeException("Player not found with id: " + playerDTO.getId());
                } else {
                    team.addPlayer(playerService.toEntity(playerOpt.get()));
                }
            }
        }
        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDto(savedTeam);
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        teamRepository.delete(team);
    }

    public List<TeamDTO> searchTeamsByName(String name) {
        return teamMapper.toDtoList(teamRepository.findByNameContainingIgnoreCase(name));
    }
}
