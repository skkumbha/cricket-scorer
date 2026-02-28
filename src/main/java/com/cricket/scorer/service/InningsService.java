package com.cricket.scorer.service;

import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.dto.MatchTeamDTO;
import com.cricket.scorer.dto.MatchTeamPlayerDTO;
import com.cricket.scorer.mapper.InningsMapper;
import com.cricket.scorer.mapper.MatchMapper;
import com.cricket.scorer.mapper.TeamMapper;
import com.cricket.scorer.model.Innings;
import com.cricket.scorer.model.Match;
import com.cricket.scorer.model.Team;
import com.cricket.scorer.repository.InningsRepository;
import com.cricket.scorer.repository.MatchPlayerRepository;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class InningsService {

    @Autowired
    private InningsRepository inningsRepository;
    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private InningsMapper inningsMapper;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private MatchPlayerRepository matchPlayerRepository;

    public static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(InningsService.class);


    public List<InningsDTO> getAllInnings() {
        return inningsMapper.toDtoList(inningsRepository.findAll());
    }

    public InningsDTO getInningsById(Long id) {
        Innings innings = inningsRepository.findById(id).get();
        return getInningsDTO(innings.getMatch().getId(), innings);
    }

    public Innings getEntityById(Long id) {
        return inningsRepository.findById(id).get();
    }

    public List<InningsDTO> getInningsByMatchId(Long matchId) {

        return inningsRepository.findByMatchIdOrderByInningsNumber(matchId)
                .stream().map(innings -> getInningsDTO(matchId, innings))
                .collect(Collectors.toList());
    }

    public Innings getInningsByMatchIdAndInningsNumber(Long matchId, Integer inningsNumber) {
        return inningsRepository.findByMatchIdAndInningsNumber(matchId, inningsNumber);
    }

    private InningsDTO getInningsDTO(Long matchId, Innings innings) {
        InningsDTO inningsDTO = inningsMapper.toDto(innings);

        Long battingTeamId = innings.getBattingTeam().getId();
        MatchTeamDTO battingTeamDTO = new MatchTeamDTO();
        battingTeamDTO.setId(battingTeamId);
        List<MatchTeamPlayerDTO> battingTeamPlayerDTOs =
                matchPlayerRepository.findByMatchIdAndTeamId(matchId, battingTeamId).
                        stream().map(matchPlayer -> new MatchTeamPlayerDTO(matchPlayer.getPlayer().getId(), matchPlayer.getPlayer().getFullName(),
                                    matchPlayer.getPlayer().getJerseyNumber(),
                                    matchPlayer.getPlayer().getRole(),
                                    matchPlayer.getPlayer().getCreatedAt())
                        ).collect(Collectors.toList());
        battingTeamDTO.setPlayers(battingTeamPlayerDTOs);

        Long bowlingTeamId = innings.getBowlingTeam().getId();
        MatchTeamDTO bowlingTeamDTO = new MatchTeamDTO();
        bowlingTeamDTO.setId(bowlingTeamId);
        List<MatchTeamPlayerDTO> bowlingTeamPlayerDTOs = matchPlayerRepository.findByMatchIdAndTeamId(matchId, bowlingTeamId).
                stream().map(matchPlayer -> new MatchTeamPlayerDTO(matchPlayer.getPlayer().getId(), matchPlayer.getPlayer().getFullName(),
                        matchPlayer.getPlayer().getJerseyNumber(),
                        matchPlayer.getPlayer().getRole(),
                        matchPlayer.getPlayer().getCreatedAt())
                        ).collect(Collectors.toList());
        bowlingTeamDTO.setPlayers(bowlingTeamPlayerDTOs);
        inningsDTO.setBattingTeamDTO(battingTeamDTO);
        inningsDTO.setBowlingTeamDTO(bowlingTeamDTO);
        return inningsDTO;
    }

    public InningsDTO createInnings(Long matchId, Integer inningsNumber, Long battingTeamId, Long bowlingTeamId) {
        Match match = matchService.getMatchEntityById(matchId);
        Team batting = teamService.getTeamEntityById(battingTeamId);
        Team bowling = teamService.getTeamEntityById(bowlingTeamId);

        Innings innings = new Innings(match, inningsNumber, batting, bowling);
        Innings savedInnings = inningsRepository.save(innings);
        LOGGER.info("Created innings with id: " + savedInnings.getId() + " for match id: " + matchId);

        updateMatchStatus(matchId, "INNINGS_" + inningsNumber, match);
        CompletableFuture.runAsync(() -> {
            LOGGER.info("Starting score creation for innings id: " + savedInnings.getId() + " INNINGS " + inningsNumber + " in match id: " + matchId + " " + match.getMatchName());
            scoreService.createScore(savedInnings, match);
        });
        return inningsMapper.toDto(savedInnings);
    }

    private void updateMatchStatus(Long matchId, String status, Match match) {
        match.setStatus(status);
        matchService.updateMatch(matchId, matchMapper.toDto(match));
        LOGGER.info("Updated match status to " + status + " for match id: " + matchId);
    }

    public InningsDTO updateInnings(Long id, Innings updates) {
        Innings existingInnings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + id));

        if (updates.getTotalRuns() != null) existingInnings.setTotalRuns(updates.getTotalRuns());
        if (updates.getTotalWickets() != null) existingInnings.setTotalWickets(updates.getTotalWickets());
        if (updates.getTotalOvers() != null) existingInnings.setTotalOvers(updates.getTotalOvers());
        if (updates.getExtras() != null) existingInnings.setExtras(updates.getExtras());
        if(updates.getIsCompleted() != null) existingInnings.setIsCompleted(updates.getIsCompleted());
        Map result = null;
        if (existingInnings.getInningsNumber() != null && existingInnings.getInningsNumber() == 2) {
            result = checkIfInningsCompleted(updates);
            if (result != null) {
                existingInnings.setIsCompleted(true);
            }
        }
        Innings savedInnings = inningsRepository.save(existingInnings);
        if (existingInnings.getIsCompleted() != null && existingInnings.getIsCompleted()) {
            Match match = matchService.getMatchEntityById(savedInnings.getMatch().getId());
            //Match match = matchService.getMatchEntityById(existingInnings.getMatch().getId());
            if (existingInnings.getInningsNumber() == 1) {
                updateMatchStatus(match.getId(), "INNINGS_1_COMPLETED", match);
                LOGGER.info("Innings id: " + id + " INNINGS_1 marked as completed. Updated match status to INNINGS_1_COMPLETED for match id: " + match.getId() + " " + match.getMatchName());
                return inningsMapper.toDto(savedInnings);
            } else if (existingInnings.getInningsNumber() == 2 && existingInnings.getIsCompleted()){
                match.setStatus("COMPLETED");
                match.setWinnerTeamId((Long) result.get("teamId"));
                match.setResultType((String) result.get("resultType"));
                updateMatchStatus(match.getId(), "COMPLETED", match);
                LOGGER.info("Innings id: " + id + " INNINGS_2 marked as completed. Updated match status to COMPLETED for match id: " + match.getId() + " " + match.getMatchName());
            }
            LOGGER.info("Innings id: " + id + " INNINGS_" + existingInnings.getInningsNumber()
                    + " marked as completed. Updated match status to COMPLETED for match id: " + match.getId() + " " + match.getMatchName());
        }
        return inningsMapper.toDto(savedInnings);
    }

    public void deleteInnings(Long id) {
        Innings innings = inningsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Innings not found with id: " + id));
        inningsRepository.delete(innings);
    }

    public Innings toEntity(InningsDTO inningsDTO) {
        return inningsMapper.toEntity(inningsDTO);
    }

    private Map checkIfInningsCompleted(Innings secondInnings) {
        Map result = null;
        if (secondInnings.getInningsNumber() == 2) {
            Innings firstInnings = getInningsByMatchIdAndInningsNumber(secondInnings.getMatch().getId(), 1);
            // Check if batting team won
            if (secondInnings.getTotalRuns() != null && secondInnings.getTotalRuns() >= firstInnings.getTotalRuns()) {
                result = new HashMap<>();
                result.put("teamId", secondInnings.getBattingTeam().getId());
                result.put("resultType", "WON");
                return result;
            }
            // Check if Bowling Team won
            if (secondInnings.getTotalWickets() != null && secondInnings.getTotalWickets() >= 10) {
                result = new HashMap<>();
                result.put("teamId", secondInnings.getBowlingTeam().getId());
                result.put("resultType", "WON");
                return result;
            }

            // Check If Overs are completed -- this condition checks the first innings over, change this to match overs
            if (secondInnings.getTotalOvers() != null && firstInnings.getTotalOvers() != null && secondInnings.getTotalOvers().compareTo(firstInnings.getTotalOvers()) >= 0) {
                if (secondInnings.getTotalRuns() != null && firstInnings.getTotalRuns() != null) {
                    if (secondInnings.getTotalRuns() > firstInnings.getTotalRuns()) {
                        result = new HashMap<>();
                        result.put("teamId", secondInnings.getBattingTeam().getId());
                        result.put("resultType", "WON");
                    } else if (secondInnings.getTotalRuns() < firstInnings.getTotalRuns()) {
                        result = new HashMap<>();
                        result.put("teamId", secondInnings.getBowlingTeam().getId());
                        result.put("resultType", "WON");
                    } else {
                        result = new HashMap<>();
                        result.put("teamId", null);
                        result.put("resultType", "DRAW");
                    }
                }
            }
        }
        return result;
    }
}

