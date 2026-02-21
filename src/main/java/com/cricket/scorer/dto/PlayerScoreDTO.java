package com.cricket.scorer.dto;

import java.math.BigDecimal;

public class PlayerScoreDTO {

    private Long id;
    private Long playerId;
    private Long teamId;
    private Long matchId;
    private Long inningId;
    private Integer runsScored;
    private Integer balls;
    private Integer runsGiven;
    private Integer wicketsTaken;
    private Integer sixes;
    private Integer fours;
    private BigDecimal oversBowled;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Long getMatchId() {
        return matchId;
    }
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
    public Long getInningId() {
        return inningId;
    }
    public void setInningId(Long inningId) {
        this.inningId = inningId;
    }
    public Integer getRunsScored() {
        return runsScored;
    }
    public void setRunsScored(Integer runsScored) {
        this.runsScored = runsScored;
    }
    public Integer getBalls() {
        return balls;
    }
    public void setBalls(Integer balls) {
        this.balls = balls;
    }
    public Integer getRunsGiven() {
        return runsGiven;
    }
    public void setRunsGiven(Integer runsGiven) {
        this.runsGiven = runsGiven;
    }
    public Integer getWicketsTaken() {
        return wicketsTaken;
    }
    public void setWicketsTaken(Integer wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }
    public Integer getSixes() {
        return sixes;
    }
    public void setSixes(Integer sixes) {
        this.sixes = sixes;
    }
    public Integer getFours() {
        return fours;
    }
    public void setFours(Integer fours) {
        this.fours = fours;
    }

    public BigDecimal getOversBowled() {
        return oversBowled;
    }
    public void setOversBowled(BigDecimal oversBowled) {
        this.oversBowled = oversBowled;
    }
}
