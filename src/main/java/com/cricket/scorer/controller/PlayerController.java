package com.cricket.scorer.controller;

import com.cricket.scorer.model.Player;
import com.cricket.scorer.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) {
        Player createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @Valid @RequestBody Player player) {
        try {
            Player updatedPlayer = playerService.updatePlayer(id, player);
            return ResponseEntity.ok(updatedPlayer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Player>> getPlayersByTeamId(@PathVariable Long teamId) {
        List<Player> players = playerService.getPlayersByTeamId(teamId);
        return ResponseEntity.ok(players);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Player>> getPlayersByRole(@PathVariable String role) {
        List<Player> players = playerService.getPlayersByRole(role);
        return ResponseEntity.ok(players);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayersByName(@RequestParam String name) {
        List<Player> players = playerService.searchPlayersByName(name);
        return ResponseEntity.ok(players);
    }
}
