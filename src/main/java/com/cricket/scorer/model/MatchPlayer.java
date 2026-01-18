package com.cricket.scorer.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "match_players")
public class MatchPlayer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
    
    @Column(name = "is_playing")
    private Boolean isPlaying = true;
    
    // Constructors
    public MatchPlayer() {}
    
    public MatchPlayer(Match match, Team team, Player player) {
        this.match = match;
        this.team = team;
        this.player = player;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Match getMatch() {
        return match;
    }
    
    public void setMatch(Match match) {
        this.match = match;
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
    
    public Boolean getIsPlaying() {
        return isPlaying;
    }
    
    public void setIsPlaying(Boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
