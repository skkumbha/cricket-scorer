package com.cricket.scorer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "balls")
public class Ball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "over_id", nullable = false)
    private Over over;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id", nullable = false)
    private Innings innings;

    @Column(name = "ball_number", nullable = false)
    private Integer ballNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batsman_id", nullable = false)
    private Player batsman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowler_id", nullable = false)
    private Player bowler;

    @Column(name = "runs_scored")
    private Integer runsScored = 0;

    @Column
    private Integer extras = 0;

    @Column(name = "extra_type", length = 20)
    private String extraType;

    @Column(name = "is_wicket")
    private Boolean isWicket = false;

    @Column(name = "wicket_type", length = 50)
    private String wicketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fielder_id")
    private Player fielder;

    @Column(name = "is_boundary")
    private Boolean isBoundary = false;

    @Column(name = "is_six")
    private Boolean isSix = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (runsScored == null) runsScored = 0;
        if (extras == null) extras = 0;
        if (isWicket == null) isWicket = false;
        if (isBoundary == null) isBoundary = false;
        if (isSix == null) isSix = false;
    }

    // Constructors
    public Ball() {
    }

    public Ball(Over over, Innings innings, Integer ballNumber, Player batsman, Player bowler) {
        this.over = over;
        this.innings = innings;
        this.ballNumber = ballNumber;
        this.batsman = batsman;
        this.bowler = bowler;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Over getOver() {
        return over;
    }

    public void setOver(Over over) {
        this.over = over;
    }

    public Innings getInnings() {
        return innings;
    }

    public void setInnings(Innings innings) {
        this.innings = innings;
    }

    public Integer getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(Integer ballNumber) {
        this.ballNumber = ballNumber;
    }

    public Player getBatsman() {
        return batsman;
    }

    public void setBatsman(Player batsman) {
        this.batsman = batsman;
    }

    public Player getBowler() {
        return bowler;
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }

    public Integer getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(Integer runsScored) {
        this.runsScored = runsScored;
    }

    public Integer getExtras() {
        return extras;
    }

    public void setExtras(Integer extras) {
        this.extras = extras;
    }

    public String getExtraType() {
        return extraType;
    }

    public void setExtraType(String extraType) {
        this.extraType = extraType;
    }

    public Boolean getIsWicket() {
        return isWicket;
    }

    public void setIsWicket(Boolean isWicket) {
        this.isWicket = isWicket;
    }

    public String getWicketType() {
        return wicketType;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }

    public Player getFielder() {
        return fielder;
    }

    public void setFielder(Player fielder) {
        this.fielder = fielder;
    }

    public Boolean getIsBoundary() {
        return isBoundary;
    }

    public void setIsBoundary(Boolean isBoundary) {
        this.isBoundary = isBoundary;
    }

    public Boolean getIsSix() {
        return isSix;
    }

    public void setIsSix(Boolean isSix) {
        this.isSix = isSix;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
