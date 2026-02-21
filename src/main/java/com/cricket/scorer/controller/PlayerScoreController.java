package com.cricket.scorer.controller;

import com.cricket.scorer.dto.PlayerScoreDTO;
import com.cricket.scorer.service.PlayerScoreService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player-scores")
public class PlayerScoreController {

    @Autowired
    private PlayerScoreService playerScoreService;

    @GetMapping("/{playerId}/{inningsId}")
    public PlayerScoreDTO getPlayerScore(@PathVariable("playerId") Long playerId, @PathVariable("inningsId")Long inningsId) {
        // Logic to fetch player score from the database using PlayerScoreRepository
        // and return it as a PlayerScoreDTO
        return playerScoreService.getPlayerScore(playerId, inningsId); // Example data
    }
}
