package com.cricket.scorer.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "match_awards")
public class MatchAward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
    
    @Column(name = "award_type")
    private String awardType;
    
    @Column(name = "notes")
    private String notes;
    
    // Constructors
    public MatchAward() {}
    
    public MatchAward(Match match, Player player, String awardType) {
        this.match = match;
        this.player = player;
        this.awardType = awardType;
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
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public String getAwardType() {
        return awardType;
    }
    
    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
