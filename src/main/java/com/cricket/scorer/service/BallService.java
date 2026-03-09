package com.cricket.scorer.service;

import com.cricket.scorer.dto.BallDTO;
import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.mapper.BallMapper;
import com.cricket.scorer.model.*;
import com.cricket.scorer.repository.BallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cricket.scorer.service.InningsService.LOGGER;

@Service
public class BallService {

    private static final int BALLS_PER_OVER = 6; // assumption

    @Autowired
    private BallRepository ballRepository;
    @Autowired
    private OverService overService;
    @Autowired
    private InningsService inningsService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private BallMapper ballMapper;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PlayerScoreService playerScoreService;
    @Autowired
    private MatchService matchService;




    public List<BallDTO> getAllBalls() {
        return ballMapper.toDtoList(ballRepository.findAll());
    }

    public Optional<BallDTO> getBallById(Long id) {
        return ballRepository.findById(id).map(ballMapper::toDto);
    }

    public List<BallDTO> getBallsByOverId(Long overId) {
        return ballMapper.toDtoList(ballRepository.findByOverIdOrderByBallNumber(overId));
    }

    public List<BallDTO> getBallsByInningsId(Long inningsId) {
        return ballMapper.toDtoList(ballRepository.findByInningsId(inningsId));
    }

    public BallDTO createBall(Long overId, Long inningsId, Integer ballNumber, Long batsmanId, Long bowlerId,
                           Integer runsScored, Integer extras, String extraType, Boolean isWicket,
                           String wicketType, Long fielderId, Boolean isBoundary, Boolean isSix) {
        BigDecimal oversDecimal = null;

        Over over = overService.getOverEntityById(overId).get();
        //InningsDTO inningsDTO = inningsService.getInningsById(inningsId);
        Innings innings = inningsService.getEntityById(inningsId);

        Player batsman = playerService.getPlayerEntityById(batsmanId).get();
        Player bowler = playerService.getPlayerEntityById(bowlerId).get();
        Player fielder = null;
        if (fielderId != null) {
            fielder = playerService.getPlayerEntityById(fielderId).get();
        }

        Ball thisBall = saveNewBall(ballNumber, runsScored, extras, extraType, isWicket, wicketType, isBoundary, isSix, over, innings, batsman, bowler, fielder);

        // Update aggregates on Over and Innings
        int runsThisBall = (thisBall.getRunsScored() == null ? 0 : thisBall.getRunsScored());
        int extrasThisBall = (thisBall.getExtras() == null ? 0 : thisBall.getExtras());
        int totalRunsWithExtras = runsThisBall + extrasThisBall;

        // Update over

        // Update innings
        Integer inningsRuns = innings.getTotalRuns();
        innings.setTotalRuns((inningsRuns == null ? 0 : inningsRuns) + totalRunsWithExtras);
        Integer inningsExtras = innings.getExtras();
        innings.setExtras((inningsExtras == null ? 0 : inningsExtras) + extrasThisBall);
        if (thisBall.getIsWicket() != null && thisBall.getIsWicket()) {
            Integer tw = innings.getTotalWickets();
            innings.setTotalWickets((tw == null ? 0 : tw) + 1);
        }
        // Calculate overs as X.Y where X = overNumber -1, Y = ballNumber (assume 6-ball overs)
        int currentOver = over.getOverNumber() - 1;
        int ballsInCurrentOver = thisBall.getBallNumber();
        boolean overCompleted = false;
        if (!isExtraBall(extraType)) {
            oversDecimal = BigDecimal.valueOf(currentOver).add(BigDecimal.valueOf(ballsInCurrentOver).movePointLeft(1));
            innings.setTotalOvers(oversDecimal);
            if (overComplete(oversDecimal)) {
                    // If we exceed 6 balls, roll over to next over
                    oversDecimal = BigDecimal.valueOf(currentOver + 1); // Move to next full over
                    innings.setTotalOvers(oversDecimal);
                    overCompleted = true;
                }
            }

        InningsDTO updatedInnings = inningsService.updateInnings(inningsId, innings);

        updateOverWithThisBall(overId, over, totalRunsWithExtras, thisBall, overCompleted);

        scoreService.updateScore(updatedInnings, oversDecimal, totalRunsWithExtras, extrasThisBall);
        playerScoreService.updatePlayerScore(ballMapper.toDto(thisBall));

        return ballMapper.toDto(thisBall);
    }

    private static boolean overComplete(BigDecimal oversDecimal) {
        return oversDecimal.movePointRight(1).intValue() % 10 >= 6;
    }

    private static boolean isExtraBall(String extraType) {
        return extraType != null
                && (extraType.equalsIgnoreCase("wide") || extraType.equalsIgnoreCase("no_ball"));
    }


    private void updateOverWithThisBall(Long overId, Over over, int totalThisBall, Ball thisBall, boolean overCompleted) {
        Integer runsInThisOver = over.getRunsConceded();
        over.setRunsConceded((runsInThisOver == null ? 0 : runsInThisOver) + totalThisBall);

        if (thisBall.getIsWicket() != null && thisBall.getIsWicket()) {
            Integer wk = over.getWicketsTaken();
            over.setWicketsTaken((wk == null ? 0 : wk) + 1);
        }
        // If this is the last ball in the over, set maiden flag appropriately
        if (thisBall.getBallNumber() != null && thisBall.getBallNumber().intValue() == BALLS_PER_OVER) {
            over.setMaiden(over.getRunsConceded() == 0);
        }
        over.setOverCompleted(overCompleted);
        overService.updateOver(overId, over);
    }

    private Ball saveNewBall(Integer ballNumber, Integer runsScored, Integer extras, String extraType, Boolean isWicket, String wicketType, Boolean isBoundary, Boolean isSix, Over over, Innings innings, Player batsman, Player bowler, Player fielder) {
        Ball ball = new Ball();
        ball.setOver(over);
        ball.setInnings(innings);
        ball.setBallNumber(ballNumber);
        ball.setBatsman(batsman);
        ball.setBowler(bowler);
        if (runsScored != null) ball.setRunsScored(runsScored);
        if (extras != null) ball.setExtras(extras);
        ball.setExtraType(extraType);
        if (isWicket != null) ball.setIsWicket(isWicket);
        ball.setWicketType(wicketType);
        if (fielder != null) ball.setFielder(fielder);
        if (isBoundary != null) ball.setIsBoundary(isBoundary);
        if (isSix != null) ball.setIsSix(isSix);

        Ball saved = ballRepository.save(ball);
        return saved;
    }

    public BallDTO updateBall(Long id, Ball updates) {
        Ball ball = ballRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ball not found with id: " + id));

        // Capture previous values to compute deltas
        int prevRuns = (ball.getRunsScored() == null ? 0 : ball.getRunsScored());
        int prevExtras = (ball.getExtras() == null ? 0 : ball.getExtras());
        boolean prevWicket = (ball.getIsWicket() == null ? false : ball.getIsWicket());

        // Apply updates
        if (updates.getBallNumber() != null) ball.setBallNumber(updates.getBallNumber());
        if (updates.getRunsScored() != null) ball.setRunsScored(updates.getRunsScored());
        if (updates.getExtras() != null) ball.setExtras(updates.getExtras());
        if (updates.getExtraType() != null) ball.setExtraType(updates.getExtraType());
        if (updates.getIsWicket() != null) ball.setIsWicket(updates.getIsWicket());
        if (updates.getWicketType() != null) ball.setWicketType(updates.getWicketType());
        if (updates.getIsBoundary() != null) ball.setIsBoundary(updates.getIsBoundary());
        if (updates.getIsSix() != null) ball.setIsSix(updates.getIsSix());

        if (updates.getBatsman() != null && updates.getBatsman().getId() != null) {
            Player batsman = playerService.getPlayerById(updates.getBatsman().getId())
                    .map(playerService::toEntity)
                    .orElseThrow(() -> new RuntimeException("Batsman not found with id: " + updates.getBatsman().getId()));
            ball.setBatsman(batsman);
        }
        if (updates.getBowler() != null && updates.getBowler().getId() != null) {
            Player bowler = playerService.getPlayerById(updates.getBowler().getId())
                    .map(playerService::toEntity)
                    .orElseThrow(() -> new RuntimeException("Bowler not found with id: " + updates.getBowler().getId()));
            ball.setBowler(bowler);
        }
        if (updates.getFielder() != null && updates.getFielder().getId() != null) {
            Player fielder = playerService.getPlayerById(updates.getFielder().getId())
                    .map(playerService::toEntity)
                    .orElseThrow(() -> new RuntimeException("Fielder not found with id: " + updates.getFielder().getId()));
            ball.setFielder(fielder);
        }

        Ball saved = ballRepository.save(ball);

        // Recompute deltas
        int newRuns = (saved.getRunsScored() == null ? 0 : saved.getRunsScored());
        int newExtras = (saved.getExtras() == null ? 0 : saved.getExtras());
        boolean newWicket = (saved.getIsWicket() == null ? false : saved.getIsWicket());

        int deltaTotal = (newRuns + newExtras) - (prevRuns + prevExtras);
        int deltaExtras = newExtras - prevExtras;
        int deltaWickets = (newWicket ? 1 : 0) - (prevWicket ? 1 : 0);

        // Update over
        Over over = saved.getOver();
        Integer prevOverRuns = over.getRunsConceded();
        over.setRunsConceded((prevOverRuns == null ? 0 : prevOverRuns) + deltaTotal);
        Integer prevOverWk = over.getWicketsTaken();
        over.setWicketsTaken((prevOverWk == null ? 0 : prevOverWk) + deltaWickets);
        overService.updateOver(over.getId(), over);

        // Update innings
        Innings innings = saved.getInnings();
        Integer prevInnsRuns = innings.getTotalRuns();
        innings.setTotalRuns((prevInnsRuns == null ? 0 : prevInnsRuns) + deltaTotal);
        Integer prevInnsExtras = innings.getExtras();
        innings.setExtras((prevInnsExtras == null ? 0 : prevInnsExtras) + deltaExtras);
        Integer prevInnsWk = innings.getTotalWickets();
        innings.setTotalWickets((prevInnsWk == null ? 0 : prevInnsWk) + deltaWickets);

        // Update overs value for innings similarly to create
        if (saved.getBallNumber() != null && over.getOverNumber() != null) {
            int fullOvers = over.getOverNumber() - 1;
            int ballsInCurrentOver = saved.getBallNumber();
            BigDecimal oversDecimal = BigDecimal.valueOf(fullOvers).add(BigDecimal.valueOf(ballsInCurrentOver).movePointLeft(1));
            innings.setTotalOvers(oversDecimal);
        }

        inningsService.updateInnings(innings.getId(), innings);

        return ballMapper.toDto(saved);
    }

    public void deleteBall(Long id) {
        Ball ball = ballRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ball not found with id: " + id));

        // Compute values to subtract
        int runs = (ball.getRunsScored() == null ? 0 : ball.getRunsScored());
        int extras = (ball.getExtras() == null ? 0 : ball.getExtras());
        boolean wicket = (ball.getIsWicket() == null ? false : ball.getIsWicket());
        int total = runs + extras;

        Over over = ball.getOver();
        Integer prevOverRuns = over.getRunsConceded();
        over.setRunsConceded((prevOverRuns == null ? 0 : prevOverRuns) - total);
        if (wicket) {
            Integer prevOverWk = over.getWicketsTaken();
            over.setWicketsTaken((prevOverWk == null ? 0 : prevOverWk) - 1);
        }
        overService.updateOver(over.getId(), over);

        Innings innings = ball.getInnings();
        Integer prevInnsRuns = innings.getTotalRuns();
        innings.setTotalRuns((prevInnsRuns == null ? 0 : prevInnsRuns) - total);
        Integer prevInnsExtras = innings.getExtras();
        innings.setExtras((prevInnsExtras == null ? 0 : prevInnsExtras) - extras);
        if (wicket) {
            Integer prevInnsWk = innings.getTotalWickets();
            innings.setTotalWickets((prevInnsWk == null ? 0 : prevInnsWk) - 1);
        }
        inningsService.updateInnings(innings.getId(), innings);
        ballRepository.delete(ball);
    }
}
