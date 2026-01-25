package com.cricket.scorer.controller;

import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        List<MatchDTO> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO) {
        MatchDTO createdMatch = matchService.createMatch(matchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @Valid @RequestBody MatchDTO matchDTO) {
        try {
            MatchDTO updatedMatch = matchService.updateMatch(id, matchDTO);
            return ResponseEntity.ok(updatedMatch);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        try {
            matchService.deleteMatch(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MatchDTO>> getMatchesByStatus(@PathVariable String status) {
        List<MatchDTO> matches = matchService.getMatchesByStatus(status);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByTeam(@PathVariable Long teamId) {
        List<MatchDTO> matches = matchService.getMatchesByTeam(teamId);
        return ResponseEntity.ok(matches);
    }
}
