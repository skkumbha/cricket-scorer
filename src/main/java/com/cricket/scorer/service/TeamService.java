package com.cricket.scorer.service;

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

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        // Update only changed fields
        updateTeam(teamDetails, team);

        // If the client provided players to associate, process them safely.
        if (teamDetails.getTeamPlayers() != null) {
            for (TeamPlayer inputTp : teamDetails.getTeamPlayers()) {
                // Determine player id from input (client should send player.id)
                Long playerId = null;
                if (inputTp.getPlayer() != null && inputTp.getPlayer().getId() != null) {
                    playerId = inputTp.getPlayer().getId();
                } else if (inputTp.getId() != null) {
                    // fallback (not ideal): maybe client sent player id into teamPlayer.id
                    playerId = inputTp.getId();
                }

                if (playerId == null) {
                    throw new RuntimeException("Player id is required to associate a TeamPlayer");
                }

                Optional<Player> player = playerService.getPlayerById(playerId);
                if (player.isEmpty()) {
                    throw new RuntimeException("Player not found with id: " + playerId);
                }

                // Create a new TeamPlayer correctly linked to the managed Team and Player
                TeamPlayer tp = new TeamPlayer(team, player.get());
                // rely on Set semantics (equals/hashCode) to prevent duplicates
                team.addTeamPlayer(tp);
            }
        }

        // Persist changes; cascade = ALL on team.teamPlayers will persist new TeamPlayer rows
        return teamRepository.save(team);
    }

    private static void updateTeam(Team teamDetails, Team team) {
        if (teamDetails.getName() != null) {
            team.setName(teamDetails.getName());
        }
        if (teamDetails.getShortName() != null) {
            team.setShortName(teamDetails.getShortName());
        }
        if (teamDetails.getLogoUrl() != null) {
            team.setLogoUrl(teamDetails.getLogoUrl());
        }
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        teamRepository.delete(team);
    }

    public List<Team> searchTeamsByName(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }
}
