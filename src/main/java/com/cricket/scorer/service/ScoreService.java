package com.cricket.scorer.service;


import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.mapper.InningsMapper;
import com.cricket.scorer.mapper.MatchMapper;
import com.cricket.scorer.mapper.TeamMapper;
import com.cricket.scorer.model.Score;
import com.cricket.scorer.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cricket.scorer.model.Innings;
import com.cricket.scorer.model.Match;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ScoreService {

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    InningsMapper inningsMapper;
    @Autowired
    MatchMapper matchMapper;
    @Autowired
    TeamMapper teamMapper;

    @Transactional
    public void updateScore(InningsDTO inningsDTO, MatchDTO matchDTO, BigDecimal currOver, Integer runs, Boolean isExtra) {
      Score updated = scoreRepository.findByInningsId(inningsDTO.getId())
                .map(existingScore -> {
                    existingScore.setId(existingScore.getId());
                    existingScore.setRuns(existingScore.getRuns() + runs);
                    existingScore.setExtras(existingScore.getExtras() + (isExtra ? runs : 0));
                    existingScore.setOvers(currOver);
                    return existingScore;
                })
                .orElseThrow(() -> new RuntimeException("Score not found for innings id: " + inningsDTO.getId()));
        scoreRepository.save(updated);
    }

    public Integer getScore(Long inningsId) {
        return scoreRepository.findByInningsId(inningsId)
                .map(Score::getRuns)
                .orElse(0);
    }

    public Integer createScore(Innings innings, Match match) {
        Score score = new Score();
        score.setInnings(innings);
        score.setMatch(match);
        score.setRuns(0);
        score.setExtras(0);
        score.setOvers(BigDecimal.ZERO);
        scoreRepository.save(score);
        return score.getRuns();
    }
}
