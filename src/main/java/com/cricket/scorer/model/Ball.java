package com.cricket.scorer.model;

import javax.persistence.*;

@Entity
@Table(name = "balls")
public class Ball {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "over_id")
    private Over over;
    
    @Column(name = "ball_number")
    private Integer ballNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "striker_id")
    private Player striker;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "non_striker_id")
    private Player nonStriker;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowler_id")
    private Player bowler;
    
    @Column(name = "runs_off_bat")
    private Integer runsOffBat = 0;
    
    @Column(name = "extras")
    private Integer extras = 0;
    
    @Column(name = "extra_type")
    private String extraType;
    
    @Column(name = "is_wicket")
    private Boolean isWicket = false;
    
    @Column(name = "wicket_type")
    private String wicketType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dismissed_player_id")
    private Player dismissedPlayer;
    
    // Constructors
    public Ball() {}
    
    public Ball(Over over, Integer ballNumber) {
        this.over = over;
        this.ballNumber = ballNumber;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Over getOver() {
        return over;
    }
    
    public void setOver(Over over) {
        this.over = over;
    }
    
    public Integer getBallNumber() {
        return ballNumber;
    }
    
    public void setBallNumber(Integer ballNumber) {
        this.ballNumber = ballNumber;
    }
    
    public Player getStriker() {
        return striker;
    }
    
    public void setStriker(Player striker) {
        this.striker = striker;
    }
    
    public Player getNonStriker() {
        return nonStriker;
    }
    
    public void setNonStriker(Player nonStriker) {
        this.nonStriker = nonStriker;
    }
    
    public Player getBowler() {
        return bowler;
    }
    
    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }
    
    public Integer getRunsOffBat() {
        return runsOffBat;
    }
    
    public void setRunsOffBat(Integer runsOffBat) {
        this.runsOffBat = runsOffBat;
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
    
    public Player getDismissedPlayer() {
        return dismissedPlayer;
    }
    
    public void setDismissedPlayer(Player dismissedPlayer) {
        this.dismissedPlayer = dismissedPlayer;
    }
}
