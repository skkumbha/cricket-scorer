package com.cricket.scorer.service;

import com.cricket.scorer.dto.BallDTO;
import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.mapper.BallMapper;
import com.cricket.scorer.model.Ball;
import com.cricket.scorer.model.Innings;
import com.cricket.scorer.model.Over;
import com.cricket.scorer.model.Player;
import com.cricket.scorer.repository.BallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

        Over over = overService.getOverById(overId)
                .map(overService::toEntity).get();
        InningsDTO inningsDTO = inningsService.getInningsById(inningsId);
        Innings innings = inningsService.toEntity(inningsDTO);

        // Ensure the over belongs to the innings
        if (!over.getInnings().getId().equals(innings.getId())) {
            throw new RuntimeException("Over " + overId + " does not belong to Innings " + inningsId);
        }

        Player batsman = playerService.getPlayerById(batsmanId)
                .map(playerService::toEntity).get();
        Player bowler = playerService.getPlayerById(bowlerId)
                .map(playerService::toEntity).get();
        Player fielder = null;
        if (fielderId != null) {
            fielder = playerService.getPlayerById(fielderId)
                    .map(playerService::toEntity).get();
        }

        Ball saved = saveNewBall(ballNumber, runsScored, extras, extraType, isWicket, wicketType, isBoundary, isSix, over, innings, batsman, bowler, fielder);

        // Update aggregates on Over and Innings
        int runsThisBall = (saved.getRunsScored() == null ? 0 : saved.getRunsScored());
        int extrasThisBall = (saved.getExtras() == null ? 0 : saved.getExtras());
        int totalThisBall = runsThisBall + extrasThisBall;

        // Update over
        Integer overRuns = over.getRunsConceded();
        over.setRunsConceded((overRuns == null ? 0 : overRuns) + totalThisBall);
        if (saved.getIsWicket() != null && saved.getIsWicket()) {
            Integer wk = over.getWicketsTaken();
            over.setWicketsTaken((wk == null ? 0 : wk) + 1);
        }
        // If this is the last ball in the over, set maiden flag appropriately
        if (saved.getBallNumber() != null && saved.getBallNumber().intValue() == BALLS_PER_OVER) {
            over.setMaiden(over.getRunsConceded() == 0);
        }
        overService.updateOver(overId, over);

        // Update innings
        Integer inningsRuns = innings.getTotalRuns();
        innings.setTotalRuns((inningsRuns == null ? 0 : inningsRuns) + totalThisBall);
        Integer inningsExtras = innings.getExtras();
        innings.setExtras((inningsExtras == null ? 0 : inningsExtras) + extrasThisBall);
        if (saved.getIsWicket() != null && saved.getIsWicket()) {
            Integer tw = innings.getTotalWickets();
            innings.setTotalWickets((tw == null ? 0 : tw) + 1);
        }
        // Calculate overs as X.Y where X = overNumber -1, Y = ballNumber (assume 6-ball overs)
        if (saved.getBallNumber() != null && over.getOverNumber() != null) {
            int fullOvers = over.getOverNumber() - 1;
            int ballsInCurrentOver = saved.getBallNumber();
            // Represent overs as e.g., 10.4 => 10 overs and 4 balls
            oversDecimal = BigDecimal.valueOf(fullOvers).add(BigDecimal.valueOf(ballsInCurrentOver).movePointLeft(1));
            if (extraType != null && (extraType.equalsIgnoreCase("wide") || extraType.equalsIgnoreCase("no_ball"))) {
                // For wides and no-balls, the ball doesn't count towards over progression
                oversDecimal = oversDecimal.subtract(BigDecimal.valueOf(0.1)); // Subtract 0.1 to negate the ball count
            }
            if (oversDecimal.movePointRight(1).intValue()%10 >= 6) {
                // If we exceed 6 balls, roll over to next over
                oversDecimal = BigDecimal.valueOf(over.getOverNumber()); // Move to next full over
            }
            innings.setTotalOvers(oversDecimal);
        }
        InningsDTO updatedInnings = inningsService.updateInnings(inningsId,innings);
        scoreService.updateScore(updatedInnings, updatedInnings.getMatchDTO(), oversDecimal, totalThisBall, extrasThisBall > 0);
        playerScoreService.updatePlayerScore(ballMapper.toDto(saved));

        return ballMapper.toDto(saved);
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
