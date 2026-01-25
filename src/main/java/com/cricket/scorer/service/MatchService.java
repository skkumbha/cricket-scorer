package com.cricket.scorer.service;

import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.mapper.MatchMapper;
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
    @Autowired
    private MatchMapper matchMapper;

    public List<MatchDTO> getAllMatches() {
        return matchMapper.toDtoList(matchRepository.findAll());
    }

    public Optional<MatchDTO> getMatchById(Long id) {
        return matchRepository.findById(id).map(matchMapper::toDto);
    }

    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = matchMapper.toEntity(matchDTO);
        Match savedMatch = matchRepository.save(match);
        return matchMapper.toDto(savedMatch);
    }

    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        
        if (matchDTO.getMatchName() != null) {
            match.setMatchName(matchDTO.getMatchName());
        }
        if (matchDTO.getMatchType() != null) {
            match.setMatchType(matchDTO.getMatchType());
        }
        if (matchDTO.getLocation() != null) {
            match.setLocation(matchDTO.getLocation());
        }
        if (matchDTO.getStartTime() != null) {
            match.setStartTime(matchDTO.getStartTime());
        }
        if (matchDTO.getStatus() != null) {
            match.setStatus(matchDTO.getStatus());
        }
        if (matchDTO.getWinnerTeamId() != null) {
            match.setWinnerTeamId(matchDTO.getWinnerTeamId());
        }
        
        Match savedMatch = matchRepository.save(match);
        return matchMapper.toDto(savedMatch);
    }

    public void deleteMatch(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        matchRepository.delete(match);
    }

    public List<MatchDTO> getMatchesByStatus(String status) {
        return matchMapper.toDtoList(matchRepository.findByStatus(status));
    }

    public List<MatchDTO> getMatchesByTeam(Long teamId) {
        // This method no longer works with the new schema
        // Teams are linked to matches through match_teams table
        return List.of();
    }
}
