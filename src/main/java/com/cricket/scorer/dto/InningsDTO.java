package com.cricket.scorer.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InningsDTO {
    
    private Long id;
    private Long matchId;
    private Integer inningsNumber;
    private Long battingTeamId;
    private Long bowlingTeamId;
    private Integer totalRuns;
    private Integer totalWickets;
    private BigDecimal totalOvers;
    private Integer extras;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    
    // Constructors
    public InningsDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMatchId() {
        return matchId;
    }
    
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
    
    public Integer getInningsNumber() {
        return inningsNumber;
    }
    
    public void setInningsNumber(Integer inningsNumber) {
        this.inningsNumber = inningsNumber;
    }
    
    public Long getBattingTeamId() {
        return battingTeamId;
    }
    
    public void setBattingTeamId(Long battingTeamId) {
        this.battingTeamId = battingTeamId;
    }
    
    public Long getBowlingTeamId() {
        return bowlingTeamId;
    }
    
    public void setBowlingTeamId(Long bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }
    
    public Integer getTotalRuns() {
        return totalRuns;
    }
    
    public void setTotalRuns(Integer totalRuns) {
        this.totalRuns = totalRuns;
    }
    
    public Integer getTotalWickets() {
        return totalWickets;
    }
    
    public void setTotalWickets(Integer totalWickets) {
        this.totalWickets = totalWickets;
    }
    
    public BigDecimal getTotalOvers() {
        return totalOvers;
    }
    
    public void setTotalOvers(BigDecimal totalOvers) {
        this.totalOvers = totalOvers;
    }
    
    public Integer getExtras() {
        return extras;
    }
    
    public void setExtras(Integer extras) {
        this.extras = extras;
    }
    
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
