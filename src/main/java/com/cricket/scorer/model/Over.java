package com.cricket.scorer.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "overs")
public class Over {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id")
    private Innings innings;
    
    @Column(name = "over_number")
    private Integer overNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowler_id")
    private Player bowler;
    
    @OneToMany(mappedBy = "over", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ball> balls = new HashSet<>();
    
    // Constructors
    public Over() {}
    
    public Over(Innings innings, Integer overNumber, Player bowler) {
        this.innings = innings;
        this.overNumber = overNumber;
        this.bowler = bowler;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Innings getInnings() {
        return innings;
    }
    
    public void setInnings(Innings innings) {
        this.innings = innings;
    }
    
    public Integer getOverNumber() {
        return overNumber;
    }
    
    public void setOverNumber(Integer overNumber) {
        this.overNumber = overNumber;
    }
    
    public Player getBowler() {
        return bowler;
    }
    
    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }
    
    public Set<Ball> getBalls() {
        return balls;
    }
    
    public void setBalls(Set<Ball> balls) {
        this.balls = balls;
    }
}
