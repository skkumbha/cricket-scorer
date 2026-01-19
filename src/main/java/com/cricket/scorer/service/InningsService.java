package com.cricket.scorer.service;

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

    public List<Innings> getAllInnings() {
        return inningsRepository.findAll();
    }

    public Optional<Innings> getInningsById(Long id) {
        return inningsRepository.findById(id);
    }

    public List<Innings> getInningsByMatchId(Long matchId) {
        return inningsRepository.findByMatchIdOrderByInningsNumber(matchId);
    }

    public Innings createInnings(Long matchId, Integer inningsNumber, Long battingTeamId, Long bowlingTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));
        Team batting = teamRepository.findById(battingTeamId)
                .orElseThrow(() -> new RuntimeException("Batting team not found with id: " + battingTeamId));
        Team bowling = teamRepository.findById(bowlingTeamId)
                .orElseThrow(() -> new RuntimeException("Bowling team not found with id: " + bowlingTeamId));

        Innings innings = new Innings(match, inningsNumber, batting, bowling);
        return inningsRepository.save(innings);
    }

    public Innings updateInnings(Long id, Innings updates) {
        Innings innings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + id));

        if (updates.getTotalRuns() != null) innings.setTotalRuns(updates.getTotalRuns());
        if (updates.getTotalWickets() != null) innings.setTotalWickets(updates.getTotalWickets());
        if (updates.getTotalOvers() != null) innings.setTotalOvers(updates.getTotalOvers());
        if (updates.getExtras() != null) innings.setExtras(updates.getExtras());
        if (updates.getIsCompleted() != null) innings.setIsCompleted(updates.getIsCompleted());

        return inningsRepository.save(innings);
    }

    public void deleteInnings(Long id) {
        Innings innings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + id));
        inningsRepository.delete(innings);
    }
}

