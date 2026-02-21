package com.cricket.scorer.service;

import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.mapper.MatchMapper;
import com.cricket.scorer.mapper.MatchTeamMapper;
import com.cricket.scorer.mapper.PlayerMapper;
import com.cricket.scorer.model.Match;
import com.cricket.scorer.model.MatchPlayer;
import com.cricket.scorer.model.Team;
import com.cricket.scorer.repository.MatchRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchTeamMapper teamMapper;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private MatchPlayerService matchPlayerService;
    @Autowired
    private MatchTeamService matchTeamService;

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MatchService.class);

    public List<MatchDTO> getAllMatches() {
        return matchMapper.toDtoList(matchRepository.findAll());
    }

    public Optional<MatchDTO> getMatchById(Long id) {
        Optional<Match> mactch = matchRepository.findById(id);
        MatchDTO matchDTO = matchMapper.toDto(mactch.get());
        matchDTO.getTeams().forEach(teamDTO -> {
            teamDTO.setPlayers(matchPlayerService.getPlayersByMatchIdAndTeamId(id, teamDTO.getId()));
        });
        return Optional.of(matchDTO);
    }

    public Match getMatchEntityById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
    }

    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = toEntity(matchDTO);
        Set<Team> teams = matchDTO.getTeams().stream()
                .map(teamDTO -> teamService.getTeamEntityById(teamDTO.getId()))
                .collect(Collectors.toSet());
        match.setTeams(teams);
        Match savedMatch = matchRepository.save(match);
       // saveMatchTeam(matchDTO);
        saveMatchPlayer(matchDTO, savedMatch);
        return matchMapper.toDto(savedMatch);
    }

    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        updateMatchObject(matchDTO, match);
        Match savedMatch = matchRepository.save(match);
        return matchMapper.toDto(savedMatch);
    }

    private void updateMatchObject(MatchDTO matchDTO, Match match) {
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
        if (matchDTO.getTournamentId() != null) {
            match.setTournamentId(matchDTO.getTournamentId());
        }
        if (matchDTO.getTossWinnerTeamId() != null) {
            match.setTossWinnerTeamId(matchDTO.getTossWinnerTeamId());
        }
        if (matchDTO.getTossDecision() != null) {
            match.setTossDecision(matchDTO.getTossDecision());
        }
        if (matchDTO.getWinnerTeamId() != null) {
            match.setWinnerTeamId(matchDTO.getWinnerTeamId());
        }
        if (matchDTO.getResultType() != null) {
            match.setResultType(matchDTO.getResultType());
        }
        if (matchDTO.getResultMargin() != null) {
            match.setResultMargin(matchDTO.getResultMargin());
        }
        if (matchDTO.getTeams() != null) {
            Set<Team> teams = matchDTO.getTeams().stream().map(teamDTO -> teamService.getTeamEntityById(teamDTO.getId()))
                    .collect(Collectors.toSet());
            match.setTeams(teams);
        }
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

    private void saveMatchPlayer(MatchDTO matchDTO, Match savedMatch) {
        matchDTO.getTeams().forEach(team -> {
        List<MatchPlayer> matchPlayers =    team.getPlayers().stream().map(player -> {
                MatchPlayer matchPlayer = new MatchPlayer();
                matchPlayer.setMatch(savedMatch);
                matchPlayer.setTeam(savedMatch.getTeams().stream().filter(t -> t.getId().equals(team.getId())).findFirst().get());
                matchPlayer.setPlayer(playerService.getPlayerEntityById(player.getId()).get());
                logger.info("Created MatchPlayer: Match ID = {}, Team ID = {}, Player ID = {}", matchDTO.getId(), team.getId(), player);
                return matchPlayer;
            }).toList();
            matchPlayerService.saveMatchPlayers(matchPlayers);
        });
    }

    private Match toEntity(MatchDTO matchDTO) {
        Match match = new Match();
        match.setId(matchDTO.getId());
        match.setMatchName(matchDTO.getMatchName());
        match.setMatchType(matchDTO.getMatchType());
        match.setLocation(matchDTO.getLocation());
        match.setStartTime(matchDTO.getStartTime());
        match.setStatus(matchDTO.getStatus());
        match.setWinnerTeamId(matchDTO.getWinnerTeamId());
        match.setTournamentId(matchDTO.getTournamentId());
        match.setTossWinnerTeamId(matchDTO.getTossWinnerTeamId());
        match.setTossDecision(matchDTO.getTossDecision());
        match.setResultType(matchDTO.getResultType());
        match.setResultMargin(matchDTO.getResultMargin());
        match.setCreatedAt(matchDTO.getCreatedAt());
        match.setUpdatedAt(matchDTO.getUpdatedAt());
        Set<Team> teams = matchDTO.getTeams().stream().map(teamDTO ->
           teamService.getTeamEntityById(teamDTO.getId())).collect(Collectors.toSet());
        match.setTeams(teams);
        return match;
    }
}
