package com.cricket.scorer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_players")
public class MatchPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "playing_role", length = 50)
    private String playingRole;

    @Column(name = "is_captain")
    private Boolean isCaptain = false;

    @Column(name = "is_wicket_keeper")
    private Boolean isWicketKeeper = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isCaptain == null) {
            isCaptain = false;
        }
        if (isWicketKeeper == null) {
            isWicketKeeper = false;
        }
    }

    // Constructors
    public MatchPlayer() {
    }

    public MatchPlayer(Match match, Player player, Team team) {
        this.match = match;
        this.player = player;
        this.team = team;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getPlayingRole() {
        return playingRole;
    }

    public void setPlayingRole(String playingRole) {
        this.playingRole = playingRole;
    }

    public Boolean getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public Boolean getIsWicketKeeper() {
        return isWicketKeeper;
    }

    public void setIsWicketKeeper(Boolean isWicketKeeper) {
        this.isWicketKeeper = isWicketKeeper;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
