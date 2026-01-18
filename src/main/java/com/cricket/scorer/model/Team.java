package com.cricket.scorer.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "short_name")
    private String shortName;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamPlayer> teamPlayers = new HashSet<>();
    
    @OneToMany(mappedBy = "team")
    private Set<MatchTeam> matchTeams = new HashSet<>();
    
    @OneToMany(mappedBy = "team")
    private Set<MatchPlayer> matchPlayers = new HashSet<>();
    
    @OneToMany(mappedBy = "battingTeam")
    private Set<Innings> battingInnings = new HashSet<>();
    
    @OneToMany(mappedBy = "bowlingTeam")
    private Set<Innings> bowlingInnings = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Team() {}
    
    public Team(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getLogoUrl() {
        return logoUrl;
    }
    
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Set<TeamPlayer> getTeamPlayers() {
        return teamPlayers;
    }
    
    public void setTeamPlayers(Set<TeamPlayer> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }
    
    public Set<MatchTeam> getMatchTeams() {
        return matchTeams;
    }
    
    public void setMatchTeams(Set<MatchTeam> matchTeams) {
        this.matchTeams = matchTeams;
    }
    
    public Set<MatchPlayer> getMatchPlayers() {
        return matchPlayers;
    }
    
    public void setMatchPlayers(Set<MatchPlayer> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }
    
    public Set<Innings> getBattingInnings() {
        return battingInnings;
    }
    
    public void setBattingInnings(Set<Innings> battingInnings) {
        this.battingInnings = battingInnings;
    }
    
    public Set<Innings> getBowlingInnings() {
        return bowlingInnings;
    }
    
    public void setBowlingInnings(Set<Innings> bowlingInnings) {
        this.bowlingInnings = bowlingInnings;
    }
}
