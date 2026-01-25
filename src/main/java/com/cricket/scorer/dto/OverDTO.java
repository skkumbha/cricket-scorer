package com.cricket.scorer.dto;

import java.time.LocalDateTime;

public class OverDTO {
    
    private Long id;
    private Long inningsId;
    private Integer overNumber;
    private Long bowlerId;
    private Integer runsConceded;
    private Integer wicketsTaken;
    private Boolean maiden;
    private LocalDateTime createdAt;
    
    // Constructors
    public OverDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getInningsId() {
        return inningsId;
    }
    
    public void setInningsId(Long inningsId) {
        this.inningsId = inningsId;
    }
    
    public Integer getOverNumber() {
        return overNumber;
    }
    
    public void setOverNumber(Integer overNumber) {
        this.overNumber = overNumber;
    }
    
    public Long getBowlerId() {
        return bowlerId;
    }
    
    public void setBowlerId(Long bowlerId) {
        this.bowlerId = bowlerId;
    }
    
    public Integer getRunsConceded() {
        return runsConceded;
    }
    
    public void setRunsConceded(Integer runsConceded) {
        this.runsConceded = runsConceded;
    }
    
    public Integer getWicketsTaken() {
        return wicketsTaken;
    }
    
    public void setWicketsTaken(Integer wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }
    
    public Boolean getMaiden() {
        return maiden;
    }
    
    public void setMaiden(Boolean maiden) {
        this.maiden = maiden;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
