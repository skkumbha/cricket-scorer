package com.cricket.scorer.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "innings")
public class Innings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batting_team_id")
    private Team battingTeam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowling_team_id")
    private Team bowlingTeam;
    
    @Column(name = "innings_number")
    private Integer inningsNumber;
    
    @Column(name = "total_runs")
    private Integer totalRuns = 0;
    
    @Column(name = "wickets")
    private Integer wickets = 0;
    
    @Column(name = "overs_completed", precision = 4, scale = 1)
    private BigDecimal oversCompleted = BigDecimal.ZERO;
    
    @OneToMany(mappedBy = "innings", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Over> overs = new HashSet<>();
    
    // Constructors
    public Innings() {}
    
    public Innings(Match match, Team battingTeam, Team bowlingTeam, Integer inningsNumber) {
        this.match = match;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
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
    
    public Team getBattingTeam() {
        return battingTeam;
    }
    
    public void setBattingTeam(Team battingTeam) {
        this.battingTeam = battingTeam;
    }
    
    public Team getBowlingTeam() {
        return bowlingTeam;
    }
    
    public void setBowlingTeam(Team bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }
    
    public Integer getInningsNumber() {
        return inningsNumber;
    }
    
    public void setInningsNumber(Integer inningsNumber) {
        this.inningsNumber = inningsNumber;
    }
    
    public Integer getTotalRuns() {
        return totalRuns;
    }
    
    public void setTotalRuns(Integer totalRuns) {
        this.totalRuns = totalRuns;
    }
    
    public Integer getWickets() {
        return wickets;
    }
    
    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }
    
    public BigDecimal getOversCompleted() {
        return oversCompleted;
    }
    
    public void setOversCompleted(BigDecimal oversCompleted) {
        this.oversCompleted = oversCompleted;
    }
    
    public Set<Over> getOvers() {
        return overs;
    }
    
    public void setOvers(Set<Over> overs) {
        this.overs = overs;
    }
}
