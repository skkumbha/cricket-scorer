package com.cricket.scorer.service;

import com.cricket.scorer.dto.PlayerDTO;
import com.cricket.scorer.mapper.PlayerMapper;
import com.cricket.scorer.repository.MatchPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchPlayerService {

    @Autowired
    private MatchPlayerRepository matchPlayerRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerMapper playerMapper;


    public List<PlayerDTO> getPlayersByMatchIdAndTeamId(Long matchId, Long teamId) {
       return matchPlayerRepository.findByMatchIdAndTeamId(matchId, teamId)
                .stream()
                .map(mp -> playerService.getPlayerById(mp.getPlayer().getId()))
                .filter(playerOpt -> playerOpt.isPresent())
                .map(playerOpt -> playerOpt.get())
                .toList();
    }
}
