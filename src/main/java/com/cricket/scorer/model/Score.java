package com.cricket.scorer.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "score", uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "innings_id"}))
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id", nullable = false)
    private Innings innings;

    @Column(columnDefinition = "integer default 0")
    private Integer runs = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer wickets = 0;

    @Column(precision = 4, scale = 1)
    private BigDecimal overs = BigDecimal.ZERO;

    @Column(columnDefinition = "integer default 0")
    private Integer extras = 0;

    @Column(columnDefinition = "current run rate")
    private BigDecimal crr = BigDecimal.ZERO;

    @Column(columnDefinition = "required run rate")
    private BigDecimal rrr = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public Score() {
    }

    public Score(Match match, Innings innings) {
        this.match = match;
        this.innings = innings;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Innings getInnings() {
        return innings;
    }

    public void setInnings(Innings innings) {
        this.innings = innings;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public BigDecimal getOvers() {
        return overs;
    }

    public void setOvers(BigDecimal overs) {
        this.overs = overs;
    }

    public Integer getExtras() {
        return extras;
    }

    public void setExtras(Integer extras) {
        this.extras = extras;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

