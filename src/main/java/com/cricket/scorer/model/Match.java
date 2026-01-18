package com.cricket.scorer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Match name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Team 1 ID is required")
    @Column(name = "team1_id", nullable = false)
    private Long team1Id;

    @NotNull(message = "Team 2 ID is required")
    @Column(name = "team2_id", nullable = false)
    private Long team2Id;

    @Column(nullable = false)
    private String venue;

    @Column(name = "match_date")
    private LocalDateTime matchDate;

    @Column(nullable = false)
    private String status; // Scheduled, In Progress, Completed, Cancelled

    @Column(name = "winner_team_id")
    private Long winnerTeamId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "Scheduled";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Match() {
    }

    public Match(String name, Long team1Id, Long team2Id, String venue, LocalDateTime matchDate) {
        this.name = name;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.venue = venue;
        this.matchDate = matchDate;
        this.status = "Scheduled";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Long team1Id) {
        this.team1Id = team1Id;
    }

    public Long getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Long team2Id) {
        this.team2Id = team2Id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
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
}
