package com.cricket.scorer.controller;

import com.cricket.scorer.dto.OverDTO;
import com.cricket.scorer.model.Over;
import com.cricket.scorer.service.OverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overs")
public class OverController {

    @Autowired
    private OverService overService;

    @GetMapping
    public ResponseEntity<List<OverDTO>> getAllOvers() {
        return ResponseEntity.ok(overService.getAllOvers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OverDTO> getOverById(@PathVariable Long id) {
        return overService.getOverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/innings/{inningsId}")
    public ResponseEntity<List<OverDTO>> getOversByInnings(@PathVariable Long inningsId) {
        return ResponseEntity.ok(overService.getOversByInningsId(inningsId));
    }

    public static class CreateOverRequest {
        public Long inningsId;
        public Integer overNumber;
        public Long bowlerId;

        public CreateOverRequest() {}
    }

    @PostMapping
    public ResponseEntity<OverDTO> createOver(@Valid @RequestBody CreateOverRequest req) {
        OverDTO created = overService.createOver(req.inningsId, req.overNumber, req.bowlerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OverDTO> updateOver(@PathVariable Long id, @Valid @RequestBody Over updates) {
        try {
            OverDTO updated = overService.updateOver(id, updates);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOver(@PathVariable Long id) {
        try {
            overService.deleteOver(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

