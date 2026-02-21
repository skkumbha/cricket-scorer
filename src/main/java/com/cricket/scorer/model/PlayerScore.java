package com.cricket.scorer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id", nullable = false)
    private Innings innings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "runs_scored")
    private Integer runsScored = 0;

    @Column
    private Integer balls = 0;

    @Column(name = "runs_given")
    private Integer runsGiven = 0;

    @Column(name = "wickets_taken")
    private Integer wicketsTaken = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer sixes = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer fours = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Innings getInnings() {
        return innings;
    }
    public void setInnings(Innings innings) {
        this.innings = innings;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(Integer runsScored) {
        this.runsScored = runsScored;
    }

    public Integer getBalls() {
        return balls;
    }

    public void setBalls(Integer balls) {
        this.balls = balls;
    }

    public Integer getRunsGiven() {
        return runsGiven;
    }

    public void setRunsGiven(Integer runsGiven) {
        this.runsGiven = runsGiven;
    }

    public Integer getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(Integer wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public Integer getSixes() {
        return sixes;
    }
    public void setSixes(Integer sixes) {
        this.sixes = sixes;
    }

    public Integer getFours() {
        return fours;
    }
    public void setFours(Integer fours) {
        this.fours = fours;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



}
