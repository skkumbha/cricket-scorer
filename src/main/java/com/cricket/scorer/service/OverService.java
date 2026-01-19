package com.cricket.scorer.service;

import com.cricket.scorer.model.Innings;
import com.cricket.scorer.model.Over;
import com.cricket.scorer.model.Player;
import com.cricket.scorer.repository.InningsRepository;
import com.cricket.scorer.repository.OverRepository;
import com.cricket.scorer.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OverService {

    @Autowired
    private OverRepository overRepository;

    @Autowired
    private InningsRepository inningsRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public List<Over> getAllOvers() {
        return overRepository.findAll();
    }

    public Optional<Over> getOverById(Long id) {
        return overRepository.findById(id);
    }

    public List<Over> getOversByInningsId(Long inningsId) {
        return overRepository.findByInningsIdOrderByOverNumber(inningsId);
    }

    public Over createOver(Long inningsId, Integer overNumber, Long bowlerId) {
        Innings innings = inningsRepository.findById(inningsId)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + inningsId));
        Player bowler = playerRepository.findById(bowlerId)
                .orElseThrow(() -> new RuntimeException("Bowler not found with id: " + bowlerId));

        Over over = new Over(innings, overNumber, bowler);
        return overRepository.save(over);
    }

    public Over updateOver(Long id, Over updates) {
        Over over = overRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Over not found with id: " + id));

        if (updates.getOverNumber() != null) over.setOverNumber(updates.getOverNumber());
        if (updates.getBowler() != null) over.setBowler(updates.getBowler());
        if (updates.getRunsConceded() != null) over.setRunsConceded(updates.getRunsConceded());
        if (updates.getWicketsTaken() != null) over.setWicketsTaken(updates.getWicketsTaken());
        if (updates.getMaiden() != null) over.setMaiden(updates.getMaiden());

        return overRepository.save(over);
    }

    public void deleteOver(Long id) {
        Over over = overRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Over not found with id: " + id));
        overRepository.delete(over);
    }
}

