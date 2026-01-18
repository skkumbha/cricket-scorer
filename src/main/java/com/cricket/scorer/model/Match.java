package com.cricket.scorer.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "matches")
public class Match {
    
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "match_name")
    private String matchName;
    
    @Column(name = "match_type")
    private String matchType = "Friendly";
    
    @Column(name = "overs", nullable = false)
    private Integer overs;
    
    @Column(name = "balls_per_over")
    private Integer ballsPerOver = 6;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "status")
    private String status = "setup";
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchTeam> matchTeams = new HashSet<>();
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchPlayer> matchPlayers = new HashSet<>();
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Innings> innings = new HashSet<>();
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchAccess> matchAccesses = new HashSet<>();
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScorerRequest> scorerRequests = new HashSet<>();
    
    @OneToMany(mappedBy = "match")
    private Set<Fixture> fixtures = new HashSet<>();
    
    @OneToMany(mappedBy = "match")
    private Set<MatchAward> matchAwards = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
    }
    
    // Constructors
    public Match() {}
    
    public Match(Integer overs) {
        this.overs = overs;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getMatchName() {
        return matchName;
    }
    
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }
    
    public String getMatchType() {
        return matchType;
    }
    
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
    
    public Integer getOvers() {
        return overs;
    }
    
    public void setOvers(Integer overs) {
        this.overs = overs;
    }
    
    public Integer getBallsPerOver() {
        return ballsPerOver;
    }
    
    public void setBallsPerOver(Integer ballsPerOver) {
        this.ballsPerOver = ballsPerOver;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
    
    public Set<Innings> getInnings() {
        return innings;
    }
    
    public void setInnings(Set<Innings> innings) {
        this.innings = innings;
    }
    
    public Set<MatchAccess> getMatchAccesses() {
        return matchAccesses;
    }
    
    public void setMatchAccesses(Set<MatchAccess> matchAccesses) {
        this.matchAccesses = matchAccesses;
    }
    
    public Set<ScorerRequest> getScorerRequests() {
        return scorerRequests;
    }
    
    public void setScorerRequests(Set<ScorerRequest> scorerRequests) {
        this.scorerRequests = scorerRequests;
    }
    
    public Set<Fixture> getFixtures() {
        return fixtures;
    }
    
    public void setFixtures(Set<Fixture> fixtures) {
        this.fixtures = fixtures;
    }
    
    public Set<MatchAward> getMatchAwards() {
        return matchAwards;
    }
    
    public void setMatchAwards(Set<MatchAward> matchAwards) {
        this.matchAwards = matchAwards;
    }
}
