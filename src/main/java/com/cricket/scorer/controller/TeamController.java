package com.cricket.scorer.controller;

import com.cricket.scorer.model.Team;
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
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @Valid @RequestBody Team team) {
        try {
            Team updatedTeam = teamService.updateTeam(id, team);
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

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Team>> getTeamsByCountry(@PathVariable String country) {
        List<Team> teams = teamService.getTeamsByCountry(country);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Team>> searchTeamsByName(@RequestParam String name) {
        List<Team> teams = teamService.searchTeamsByName(name);
        return ResponseEntity.ok(teams);
    }
}
