package com.cricket.scorer.service;

import com.cricket.scorer.model.Match;
import com.cricket.scorer.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, Match matchDetails) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        
        match.setName(matchDetails.getName());
        match.setTeam1Id(matchDetails.getTeam1Id());
        match.setTeam2Id(matchDetails.getTeam2Id());
        match.setVenue(matchDetails.getVenue());
        match.setMatchDate(matchDetails.getMatchDate());
        match.setStatus(matchDetails.getStatus());
        match.setWinnerTeamId(matchDetails.getWinnerTeamId());
        
        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        matchRepository.delete(match);
    }

    public List<Match> getMatchesByStatus(String status) {
        return matchRepository.findByStatus(status);
    }

    public List<Match> getMatchesByTeam(Long teamId) {
        return matchRepository.findByTeam1IdOrTeam2Id(teamId, teamId);
    }

    public List<Match> searchMatchesByVenue(String venue) {
        return matchRepository.findByVenueContainingIgnoreCase(venue);
    }
}
