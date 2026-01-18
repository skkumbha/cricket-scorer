package com.cricket.scorer.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name = "jersey_number")
    private Integer jerseyNumber;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamPlayer> teamPlayers = new HashSet<>();
    
    @OneToMany(mappedBy = "player")
    private Set<MatchPlayer> matchPlayers = new HashSet<>();
    
    @OneToMany(mappedBy = "bowler")
    private Set<Over> overs = new HashSet<>();
    
    @OneToMany(mappedBy = "striker")
    private Set<Ball> strikerBalls = new HashSet<>();
    
    @OneToMany(mappedBy = "nonStriker")
    private Set<Ball> nonStrikerBalls = new HashSet<>();
    
    @OneToMany(mappedBy = "bowler")
    private Set<Ball> bowlerBalls = new HashSet<>();
    
    @OneToMany(mappedBy = "dismissedPlayer")
    private Set<Ball> dismissedBalls = new HashSet<>();
    
    @OneToMany(mappedBy = "player")
    private Set<MatchAward> matchAwards = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Player() {}
    
    public Player(String fullName) {
        this.fullName = fullName;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Integer getJerseyNumber() {
        return jerseyNumber;
    }
    
    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
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
    
    public Set<MatchPlayer> getMatchPlayers() {
        return matchPlayers;
    }
    
    public void setMatchPlayers(Set<MatchPlayer> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }
    
    public Set<Over> getOvers() {
        return overs;
    }
    
    public void setOvers(Set<Over> overs) {
        this.overs = overs;
    }
    
    public Set<Ball> getStrikerBalls() {
        return strikerBalls;
    }
    
    public void setStrikerBalls(Set<Ball> strikerBalls) {
        this.strikerBalls = strikerBalls;
    }
    
    public Set<Ball> getNonStrikerBalls() {
        return nonStrikerBalls;
    }
    
    public void setNonStrikerBalls(Set<Ball> nonStrikerBalls) {
        this.nonStrikerBalls = nonStrikerBalls;
    }
    
    public Set<Ball> getBowlerBalls() {
        return bowlerBalls;
    }
    
    public void setBowlerBalls(Set<Ball> bowlerBalls) {
        this.bowlerBalls = bowlerBalls;
    }
    
    public Set<Ball> getDismissedBalls() {
        return dismissedBalls;
    }
    
    public void setDismissedBalls(Set<Ball> dismissedBalls) {
        this.dismissedBalls = dismissedBalls;
    }
    
    public Set<MatchAward> getMatchAwards() {
        return matchAwards;
    }
    
    public void setMatchAwards(Set<MatchAward> matchAwards) {
        this.matchAwards = matchAwards;
    }
}
