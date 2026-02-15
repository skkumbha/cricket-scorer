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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.List;
import java.util.Optional;
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


    public List<InningsDTO> getAllInnings() {
        return inningsMapper.toDtoList(inningsRepository.findAll());
    }

    public InningsDTO getInningsById(Long id) {
        Innings innings = inningsRepository.findById(id).get();
        return getInningsDTO(innings.getMatch().getId(), innings);
    }

    public List<InningsDTO> getInningsByMatchId(Long matchId) {

        return inningsRepository.findByMatchIdOrderByInningsNumber(matchId)
                .stream().map(innings -> getInningsDTO(matchId, innings))
                .collect(Collectors.toList());
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
        Match match = matchService.getMatchById(matchId)
                .map(matchMapper::toEntity)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));
        Team batting = teamService.getTeamById(battingTeamId)
                .map(teamMapper::toEntity)
                .orElseThrow(() -> new RuntimeException("Batting team not found with id: " + battingTeamId));
        Team bowling = teamService.getTeamById(bowlingTeamId)
                .map(teamMapper::toEntity)
                .orElseThrow(() -> new RuntimeException("Bowling team not found with id: " + bowlingTeamId));

        Innings innings = new Innings(match, inningsNumber, batting, bowling);
        Innings savedInnings = inningsRepository.save(innings);
        CompletableFuture.runAsync(() -> {
            scoreService.createScore(savedInnings, match);
        });
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

    public Innings toEntity(InningsDTO inningsDTO) {
        return inningsMapper.toEntity(inningsDTO);
    }
}

