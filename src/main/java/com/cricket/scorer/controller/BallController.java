package com.cricket.scorer.controller;

import com.cricket.scorer.dto.BallDTO;
import com.cricket.scorer.model.Ball;
import com.cricket.scorer.service.BallService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balls")
public class BallController {

    @Autowired
    private BallService ballService;

    @GetMapping
    public ResponseEntity<List<BallDTO>> getAllBalls() {
        return ResponseEntity.ok(ballService.getAllBalls());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BallDTO> getBallById(@PathVariable Long id) {
        return ballService.getBallById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/over/{overId}")
    public ResponseEntity<List<BallDTO>> getBallsByOver(@PathVariable Long overId) {
        return ResponseEntity.ok(ballService.getBallsByOverId(overId));
    }

    @GetMapping("/innings/{inningsId}")
    public ResponseEntity<List<BallDTO>> getBallsByInnings(@PathVariable Long inningsId) {
        return ResponseEntity.ok(ballService.getBallsByInningsId(inningsId));
    }

    public static class CreateBallRequest {
        public Long overId;
        public Long inningsId;
        public Integer ballNumber;
        public Long batsmanId;
        public Long bowlerId;
        public Integer runsScored;
        public Integer extras;
        public String extraType;
        public Boolean isWicket;
        public String wicketType;
        public Long fielderId;
        public Boolean isBoundary;
        public Boolean isSix;

        public CreateBallRequest() {}
    }

    @PostMapping
    public ResponseEntity<BallDTO> createBall(@Valid @RequestBody CreateBallRequest req) {
        BallDTO created = ballService.createBall(
                req.overId,
                req.inningsId,
                req.ballNumber,
                req.batsmanId,
                req.bowlerId,
                req.runsScored,
                req.extras,
                req.extraType,
                req.isWicket,
                req.wicketType,
                req.fielderId,
                req.isBoundary,
                req.isSix
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BallDTO> updateBall(@PathVariable Long id, @Valid @RequestBody Ball updates) {
        try {
            BallDTO updated = ballService.updateBall(id, updates);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBall(@PathVariable Long id) {
        try {
            ballService.deleteBall(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

