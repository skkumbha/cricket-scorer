package com.cricket.scorer.service;

import com.cricket.scorer.dto.OverDTO;
import com.cricket.scorer.mapper.OverMapper;
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
    
    @Autowired
    private OverMapper overMapper;

    public List<OverDTO> getAllOvers() {
        return overMapper.toDtoList(overRepository.findAll());
    }

    public Optional<OverDTO> getOverById(Long id) {
        return overRepository.findById(id).map(overMapper::toDto);
    }

    public List<OverDTO> getOversByInningsId(Long inningsId) {
        return overMapper.toDtoList(overRepository.findByInningsIdOrderByOverNumber(inningsId));
    }

    public OverDTO createOver(Long inningsId, Integer overNumber, Long bowlerId) {
        Innings innings = inningsRepository.findById(inningsId)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + inningsId));
        Player bowler = playerRepository.findById(bowlerId)
                .orElseThrow(() -> new RuntimeException("Bowler not found with id: " + bowlerId));

        Over over = new Over(innings, overNumber, bowler);
        Over savedOver = overRepository.save(over);
        return overMapper.toDto(savedOver);
    }

    public OverDTO updateOver(Long id, Over updates) {
        Over over = overRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Over not found with id: " + id));

        if (updates.getOverNumber() != null) over.setOverNumber(updates.getOverNumber());
        if (updates.getBowler() != null) over.setBowler(updates.getBowler());
        if (updates.getRunsConceded() != null) over.setRunsConceded(updates.getRunsConceded());
        if (updates.getWicketsTaken() != null) over.setWicketsTaken(updates.getWicketsTaken());
        if (updates.getMaiden() != null) over.setMaiden(updates.getMaiden());

        Over savedOver = overRepository.save(over);
        return overMapper.toDto(savedOver);
    }

    public void deleteOver(Long id) {
        Over over = overRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Over not found with id: " + id));
        overRepository.delete(over);
    }

    public Over toEntity(OverDTO overDTO) {
        return overMapper.toEntity(overDTO);
    }

}

