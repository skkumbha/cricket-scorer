package com.cricket.scorer.service;


import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.dto.TeamDTO;
import com.cricket.scorer.mapper.InningsMapper;
import com.cricket.scorer.mapper.MatchMapper;
import com.cricket.scorer.mapper.TeamMapper;
import com.cricket.scorer.model.Score;
import com.cricket.scorer.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cricket.scorer.model.Innings;
import com.cricket.scorer.model.Match;
import com.cricket.scorer.model.Team;
import org.springframework.transaction.annotation.Transactional;

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
    public void addScore(InningsDTO inningsDTO, MatchDTO matchDTO, TeamDTO battingTeamDTO, Integer runs, Boolean isExtra) {
      Score updated = scoreRepository.findByInningsIdAndTeamId(inningsDTO.getId(), battingTeamDTO.getId())
                .map(existingScore -> {
                    existingScore.setId(existingScore.getId());
                    existingScore.setRuns(existingScore.getRuns() + runs);
                    existingScore.setExtras(existingScore.getExtras() + (isExtra ? runs : 0));
                    return existingScore;
                })
                .orElseGet(() -> {
                    Score score = new Score();
                    score.setInnings(inningsMapper.toEntity(inningsDTO));
                    score.setMatch(matchMapper.toEntity(matchDTO));
                    score.setTeam(teamMapper.toEntity(battingTeamDTO));
                    score.setRuns(runs);
                    score.setExtras(isExtra ? runs : 0);
                    return score;
            });
        scoreRepository.save(updated);
    }

    public Integer getScore(Long inningsId, Long teamId) {
        return scoreRepository.findByInningsIdAndTeamId(inningsId, teamId)
                .map(Score::getRuns)
                .orElse(0);
    }

    public Integer createScore(Innings innings, Match match, Team team) {
        Score score = new Score();
        score.setInnings(innings);
        score.setMatch(match);
        score.setTeam(team);
        score.setRuns(0);
        score.setExtras(0);
        scoreRepository.save(score);
        return score.getRuns();
    }
}
