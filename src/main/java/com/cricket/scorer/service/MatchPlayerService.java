package com.cricket.scorer.service;

import com.cricket.scorer.dto.MatchTeamPlayerDTO;
import com.cricket.scorer.dto.PlayerDTO;
import com.cricket.scorer.mapper.PlayerMapper;
import com.cricket.scorer.model.MatchPlayer;
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


    public List<MatchTeamPlayerDTO> getPlayersByMatchIdAndTeamId(Long matchId, Long teamId) {
       return matchPlayerRepository.findByMatchIdAndTeamId(matchId, teamId)
                .stream()
                .map(mp ->  {
                    MatchTeamPlayerDTO dto = new MatchTeamPlayerDTO();
                    dto.setId(mp.getPlayer().getId());
                    dto.setFullName(mp.getPlayer().getFullName());
                    dto.setRole(mp.getPlayer().getRole());
                    dto.setJerseyNumber(mp.getPlayer().getJerseyNumber());
                    return dto;
                })
                .toList();
    }

    public void saveMatchPlayer(MatchPlayer matchPlayer) {
        matchPlayerRepository.save(matchPlayer);
    }

    public void saveMatchPlayers(List<MatchPlayer> matchPlayers) {
        matchPlayerRepository.saveAll(matchPlayers);
    }
}
