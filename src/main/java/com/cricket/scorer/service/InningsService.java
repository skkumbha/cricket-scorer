package com.cricket.scorer.service;

import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.mapper.InningsMapper;
import com.cricket.scorer.model.Innings;
import com.cricket.scorer.model.Match;
import com.cricket.scorer.model.Team;
import com.cricket.scorer.repository.InningsRepository;
import com.cricket.scorer.repository.MatchRepository;
import com.cricket.scorer.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InningsService {

    @Autowired
    private InningsRepository inningsRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private InningsMapper inningsMapper;

    public List<InningsDTO> getAllInnings() {
        return inningsMapper.toDtoList(inningsRepository.findAll());
    }

    public Optional<InningsDTO> getInningsById(Long id) {
        return inningsRepository.findById(id).map(inningsMapper::toDto);
    }

    public List<InningsDTO> getInningsByMatchId(Long matchId) {
        return inningsMapper.toDtoList(inningsRepository.findByMatchIdOrderByInningsNumber(matchId));
    }

    public InningsDTO createInnings(Long matchId, Integer inningsNumber, Long battingTeamId, Long bowlingTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));
        Team batting = teamRepository.findById(battingTeamId)
                .orElseThrow(() -> new RuntimeException("Batting team not found with id: " + battingTeamId));
        Team bowling = teamRepository.findById(bowlingTeamId)
                .orElseThrow(() -> new RuntimeException("Bowling team not found with id: " + bowlingTeamId));

        Innings innings = new Innings(match, inningsNumber, batting, bowling);
        Innings savedInnings = inningsRepository.save(innings);
        return inningsMapper.toDto(savedInnings);
    }

    public InningsDTO updateInnings(Long id, Innings updates) {
        Innings innings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + id));

        if (updates.getTotalRuns() != null) innings.setTotalRuns(updates.getTotalRuns());
        if (updates.getTotalWickets() != null) innings.setTotalWickets(updates.getTotalWickets());
        if (updates.getTotalOvers() != null) innings.setTotalOvers(updates.getTotalOvers());
        if (updates.getExtras() != null) innings.setExtras(updates.getExtras());
        if (updates.getIsCompleted() != null) innings.setIsCompleted(updates.getIsCompleted());

        Innings savedInnings = inningsRepository.save(innings);
        return inningsMapper.toDto(savedInnings);
    }

    public void deleteInnings(Long id) {
        Innings innings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + id));
        inningsRepository.delete(innings);
    }
}

