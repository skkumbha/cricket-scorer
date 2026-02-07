package com.cricket.scorer.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.time.LocalDateTime;

public class MatchDTO {
    
    private Long id;
    
    @NotBlank(message = "Match name is required")
    private String matchName;
    
    private String matchType;
    private String location;
    private LocalDateTime startTime;
    private String status;
    private Long tournamentId;
    private Long tossWinnerTeamId;
    private String tossDecision;
    private Long winnerTeamId;
    private String resultType;
    private String resultMargin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TeamDTO> teams;
    
    // Constructors
    public MatchDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMatchName() {
        return matchName;
    }
    
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }
    
    public String getMatchType() {
        return matchType;
    }
    
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getTournamentId() {
        return tournamentId;
    }
    
    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }
    
    public Long getTossWinnerTeamId() {
        return tossWinnerTeamId;
    }
    
    public void setTossWinnerTeamId(Long tossWinnerTeamId) {
        this.tossWinnerTeamId = tossWinnerTeamId;
    }
    
    public String getTossDecision() {
        return tossDecision;
    }
    
    public void setTossDecision(String tossDecision) {
        this.tossDecision = tossDecision;
    }
    
    public Long getWinnerTeamId() {
        return winnerTeamId;
    }
    
    public void setWinnerTeamId(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }
    
    public String getResultType() {
        return resultType;
    }
    
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
    
    public String getResultMargin() {
        return resultMargin;
    }
    
    public void setResultMargin(String resultMargin) {
        this.resultMargin = resultMargin;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDTO> teams) {
        this.teams = teams;
    }

    public List<TeamDTO> addTeam(TeamDTO team) {
        this.teams.add(team);
        return this.teams;
    }
}
