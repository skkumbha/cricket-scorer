package com.cricket.scorer.service;

import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.mapper.MatchMapper;
import com.cricket.scorer.mapper.PlayerMapper;
import com.cricket.scorer.mapper.TeamMapper;
import com.cricket.scorer.model.Match;
import com.cricket.scorer.model.MatchPlayer;
import com.cricket.scorer.model.MatchTeam;
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
    private TeamMapper teamMapper;
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
        return matchDTO != null ? Optional.of(matchDTO) : Optional.empty();
    }

    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = matchMapper.toEntity(matchDTO);
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
        if (matchDTO.getTeams() != null) {
            matchDTO.getTeams().forEach(teamDTO -> {
                Optional<Team> team = teamService.getTeamById(teamDTO.getId()).map(teamMapper::toEntity);
                match.addTeam(team.get());
                teamDTO.getPlayers().forEach(playerDTO -> {
                    MatchPlayer matchPlayer = new MatchPlayer();
                    matchPlayer.setMatch(match);
                    matchPlayer.setTeam(team.get());
                    matchPlayer.setPlayer(playerService.getPlayerById(playerDTO.getId()).map(playerMapper::toEntity).get());
                    match.addMatchPlayer(matchPlayer);
                });
            });
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

    private void saveMatchTeam(MatchDTO matchDTO) {
        matchDTO.getTeams().forEach(team -> {
            MatchTeam matchTeam = new MatchTeam();
            matchTeam.setTeam(teamMapper.toEntity(team));
            matchTeam.setMatch(matchMapper.toEntity(matchDTO));
            matchTeamService.saveMatchTeam(matchTeam);
            logger.info("Saved MatchTeam: Match ID = {}, Team ID = {}", matchDTO.getId(), team.getId());
        });

    }

    private void saveMatchPlayer(MatchDTO matchDTO, Match savedMatch) {
        matchDTO.getTeams().forEach(team -> {
            team.getPlayerIds().forEach(player -> {
                MatchPlayer matchPlayer = new MatchPlayer();
                matchPlayer.setMatch(savedMatch);
                matchPlayer.setTeam(teamMapper.toEntity(team));
                matchPlayer.setPlayer(playerService.getPlayerEntityById(player).get());
                matchPlayerService.saveMatchPlayer(matchPlayer);
                logger.info("Saved MatchPlayer: Match ID = {}, Team ID = {}, Player ID = {}", matchDTO.getId(), team.getId(), player);
            });
        });
    }
}
