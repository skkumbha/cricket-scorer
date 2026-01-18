package com.cricket.scorer.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "team_players")
public class TeamPlayer implements Serializable {
    
    @EmbeddedId
    private TeamPlayerId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;
    
    // Constructors
    public TeamPlayer() {}
    
    public TeamPlayer(Team team, Player player) {
        this.team = team;
        this.player = player;
        this.id = new TeamPlayerId(team.getId(), player.getId());
    }
    
    // Getters and Setters
    public TeamPlayerId getId() {
        return id;
    }
    
    public void setId(TeamPlayerId id) {
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamPlayer)) return false;
        TeamPlayer that = (TeamPlayer) o;
        return Objects.equals(team, that.team) && Objects.equals(player, that.player);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(team, player);
    }
}

@Embeddable
class TeamPlayerId implements Serializable {
    
    @Column(name = "team_id")
    private Integer teamId;
    
    @Column(name = "player_id")
    private Integer playerId;
    
    public TeamPlayerId() {}
    
    public TeamPlayerId(Integer teamId, Integer playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }
    
    public Integer getTeamId() {
        return teamId;
    }
    
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
    
    public Integer getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamPlayerId)) return false;
        TeamPlayerId that = (TeamPlayerId) o;
        return Objects.equals(teamId, that.teamId) && Objects.equals(playerId, that.playerId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(teamId, playerId);
    }
}
