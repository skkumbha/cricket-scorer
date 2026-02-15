package com.cricket.scorer.service;

import com.cricket.scorer.model.MatchTeam;
import com.cricket.scorer.repository.MatchTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchTeamService {

    @Autowired
    private MatchTeamRepository matchTeamRepository;

    public void saveMatchTeam(MatchTeam matchTeam) {
        matchTeamRepository.save(matchTeam);
    }
}
