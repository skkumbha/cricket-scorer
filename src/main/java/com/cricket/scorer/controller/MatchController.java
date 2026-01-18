package com.cricket.scorer.controller;

import com.cricket.scorer.model.Match;
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
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Match> createMatch(@Valid @RequestBody Match match) {
        Match createdMatch = matchService.createMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable Long id, @Valid @RequestBody Match match) {
        try {
            Match updatedMatch = matchService.updateMatch(id, match);
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
    public ResponseEntity<List<Match>> getMatchesByStatus(@PathVariable String status) {
        List<Match> matches = matchService.getMatchesByStatus(status);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Match>> getMatchesByTeam(@PathVariable Long teamId) {
        List<Match> matches = matchService.getMatchesByTeam(teamId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Match>> searchMatchesByVenue(@RequestParam String venue) {
        List<Match> matches = matchService.searchMatchesByVenue(venue);
        return ResponseEntity.ok(matches);
    }
}
