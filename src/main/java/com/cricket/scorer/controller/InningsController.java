package com.cricket.scorer.controller;

import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.model.Innings;
import com.cricket.scorer.service.InningsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/innings")
public class InningsController {

    @Autowired
    private InningsService inningsService;

    @GetMapping
    public ResponseEntity<List<InningsDTO>> getAllInnings() {
        return ResponseEntity.ok(inningsService.getAllInnings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InningsDTO> getInningsById(@PathVariable Long id) {
        InningsDTO inningsDTO = inningsService.getInningsById(id);
        if (inningsDTO != null) {
            return ResponseEntity.ok(inningsDTO);
        } else {
           return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<InningsDTO>> getInningsByMatch(@PathVariable Long matchId) {
        return ResponseEntity.ok(inningsService.getInningsByMatchId(matchId));
    }

    public static class CreateInningsRequest {
        public Long matchId;
        public Integer inningsNumber;
        public Long battingTeamId;
        public Long bowlingTeamId;

        public CreateInningsRequest() {}
    }

    @PostMapping
    public ResponseEntity<InningsDTO> createInnings(@Valid @RequestBody CreateInningsRequest req) {
        InningsDTO created = inningsService.createInnings(req.matchId, req.inningsNumber, req.battingTeamId, req.bowlingTeamId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    public static class UpdateInningsRequest {
        public Integer totalRuns;
        public Integer totalWickets;
        public BigDecimal totalOvers;
        public Integer extras;
        public Boolean isCompleted;

        public UpdateInningsRequest() {}
    }

    @PutMapping("/{id}")
    public ResponseEntity<InningsDTO> updateInnings(@PathVariable Long id, @Valid @RequestBody UpdateInningsRequest req) {
        try {
            Innings updates = new Innings();
            updates.setTotalRuns(req.totalRuns);
            updates.setTotalWickets(req.totalWickets);
            updates.setTotalOvers(req.totalOvers);
            updates.setExtras(req.extras);
            updates.setIsCompleted(req.isCompleted);

            InningsDTO updated = inningsService.updateInnings(id, updates);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInnings(@PathVariable Long id) {
        try {
            inningsService.deleteInnings(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

