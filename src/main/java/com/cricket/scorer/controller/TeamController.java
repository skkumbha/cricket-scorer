package com.cricket.scorer.controller;

import com.cricket.scorer.dto.TeamDTO;
import com.cricket.scorer.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamDTO teamDTO) {
        TeamDTO createdTeam = teamService.createTeam(teamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamDTO teamDTO) {
        try {
            TeamDTO updatedTeam = teamService.updateTeam(id, teamDTO);
            return ResponseEntity.ok(updatedTeam);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamDTO> partiallyUpdateTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        try {
            TeamDTO updatedTeam = teamService.updateTeam(id, teamDTO);
            return ResponseEntity.ok(updatedTeam);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TeamDTO>> searchTeamsByName(@RequestParam String name) {
        List<TeamDTO> teams = teamService.searchTeamsByName(name);
        return ResponseEntity.ok(teams);
    }
}
