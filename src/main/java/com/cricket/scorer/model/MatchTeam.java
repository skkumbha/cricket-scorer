package com.cricket.scorer.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "match_teams")
public class MatchTeam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    
    @Column(name = "innings_number")
    private Integer inningsNumber;
    
    // Constructors
    public MatchTeam() {}
    
    public MatchTeam(Match match, Team team, Integer inningsNumber) {
        this.match = match;
        this.team = team;
        this.inningsNumber = inningsNumber;
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
    
    public Integer getInningsNumber() {
        return inningsNumber;
    }
    
    public void setInningsNumber(Integer inningsNumber) {
        this.inningsNumber = inningsNumber;
    }
}
