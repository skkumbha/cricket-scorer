package com.cricket.scorer.service;

import com.cricket.scorer.dto.BallDTO;
import com.cricket.scorer.dto.PlayerScoreDTO;
import com.cricket.scorer.model.PlayerScore;
import com.cricket.scorer.repository.PlayerScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PlayerScoreService {

    @Autowired
    private PlayerScoreRepository playerScoreRepository;

    @Autowired
    private InningsService inningsService;

    @Autowired
    private PlayerService playerService;

    public void updatePlayerScore(BallDTO ballDTO) {
        // Logic to update player's score based on runs scored and wicket status
        // This would typically involve fetching the PlayerScore entity, updating it, and saving it back to the database
        Long bowlerId = ballDTO.getBowlerId();
        Long batsmanId = ballDTO.getBatsmanId();
        Long inningsId = ballDTO.getInningsId();

        //update batsman score

        if (!isExtra(ballDTO)) {
            PlayerScore batsmanScore = playerScoreRepository.findByPlayerIdAndInningsId(batsmanId, inningsId);
            if (batsmanScore == null) {
                batsmanScore = new PlayerScore();
                batsmanScore.setPlayer(playerService.getPlayerEntityById(batsmanId).orElseThrow(() -> new RuntimeException("Player not found with id: " + batsmanId)));
                batsmanScore.setInnings(inningsService.getEntityById(inningsId));
                batsmanScore.setRunsScored(ballDTO.getRunsScored());
                batsmanScore.setBalls(1);
                batsmanScore.setFours(ballDTO.getRunsScored() == 4 ? 1 : 0);
                batsmanScore.setSixes(ballDTO.getRunsScored() == 6 ? 1 : 0);
            } else {
                batsmanScore.setRunsScored(batsmanScore.getRunsScored() + ballDTO.getRunsScored());
                batsmanScore.setBalls(batsmanScore.getBalls() + 1);
                batsmanScore.setFours(batsmanScore.getFours() + (ballDTO.getRunsScored() == 4 ? 1 : 0));
                batsmanScore.setSixes(batsmanScore.getSixes() + (ballDTO.getRunsScored() == 6 ? 1 : 0));
            }
            playerScoreRepository.save(batsmanScore);
        }

        //update bowler score
        PlayerScore bowlerScore = playerScoreRepository.findByPlayerIdAndInningsId(bowlerId, inningsId);
        if (bowlerScore == null) {
            bowlerScore = new PlayerScore();
            bowlerScore.setPlayer(playerService.getPlayerEntityById(bowlerId).orElseThrow(() -> new RuntimeException("Player not found with id: " + bowlerId)));
            bowlerScore.setInnings(inningsService.getEntityById(inningsId));
            bowlerScore.setRunsGiven(ballDTO.getRunsScored() + (ballDTO.getExtras() != null ? ballDTO.getExtras() : 0));
            bowlerScore.setBalls(isExtra(ballDTO) ? 0 : 1);
            bowlerScore.setWicketsTaken(ballDTO.getIsWicket() ? 1 : 0);
        } else {
            bowlerScore.setRunsGiven(bowlerScore.getRunsGiven() + ballDTO.getRunsScored() + (ballDTO.getExtras() != null ? ballDTO.getExtras() : 0));
            bowlerScore.setBalls(bowlerScore.getBalls() + (isExtra(ballDTO) ? 0 : 1));
            bowlerScore.setWicketsTaken(bowlerScore.getWicketsTaken() + (ballDTO.getIsWicket() ? 1 : 0));
        }
        playerScoreRepository.save(bowlerScore);
    }

    public PlayerScoreDTO getPlayerScore(Long playerId, Long inningsId) {
        PlayerScore playerScore = playerScoreRepository.findByPlayerIdAndInningsId(playerId, inningsId);
        if (playerScore == null) {
            throw new RuntimeException("Player score not found for player id: " + playerId + " and innings id: " + inningsId);
        }
        PlayerScoreDTO playerScoreDTO = new PlayerScoreDTO();
        playerScoreDTO.setPlayerId(playerId);
        playerScoreDTO.setInningId(inningsId);
        playerScoreDTO.setRunsScored(playerScore.getRunsScored());
        playerScoreDTO.setBalls(playerScore.getBalls());
        playerScoreDTO.setRunsGiven(playerScore.getRunsGiven());
        playerScoreDTO.setWicketsTaken(playerScore.getWicketsTaken());
        playerScoreDTO.setFours(playerScore.getFours());
        playerScoreDTO.setSixes(playerScore.getSixes());
        playerScoreDTO.setOversBowled(calculateOversBowled(playerScore.getBalls()));
        return playerScoreDTO;
    }

    private boolean isExtra(BallDTO ballDTO) {
        return "WIDE".equalsIgnoreCase(ballDTO.getExtraType()) || "NO_BALL".equalsIgnoreCase(ballDTO.getExtraType());
    }

    private BigDecimal calculateOversBowled(Integer balls) {
        int overs = balls / 6;
        int remainingBalls = balls % 6;

        BigDecimal cricketOvers = BigDecimal.valueOf(overs)
                .add(BigDecimal.valueOf(remainingBalls).divide(BigDecimal.TEN));
        return cricketOvers;
    }
}
