package com.cricket.scorer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "team_players", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"team_id", "player_id"})
})
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public TeamPlayer() {
    }

    public TeamPlayer(Team team, Player player) {
        this.team = team;
        this.player = player;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Equality based on (team.id, player.id) so Set semantics work for associations
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamPlayer)) return false;
        TeamPlayer that = (TeamPlayer) o;
        Long thisTeamId = this.team != null ? this.team.getId() : null;
        Long thatTeamId = that.team != null ? that.team.getId() : null;
        Long thisPlayerId = this.player != null ? this.player.getId() : null;
        Long thatPlayerId = that.player != null ? that.player.getId() : null;
        return Objects.equals(thisTeamId, thatTeamId) && Objects.equals(thisPlayerId, thatPlayerId);
    }

    @Override
    public int hashCode() {
        Long teamId = team != null ? team.getId() : null;
        Long playerId = player != null ? player.getId() : null;
        return Objects.hash(teamId, playerId);
    }
}
