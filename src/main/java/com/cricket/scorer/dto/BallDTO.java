package com.cricket.scorer.dto;

import java.time.LocalDateTime;

public class BallDTO {
    
    private Long id;
    private Long overId;
    private Long inningsId;
    private Integer ballNumber;
    private Long batsmanId;
    private Long bowlerId;
    private Integer runsScored;
    private Integer extras;
    private String extraType;
    private Boolean isWicket;
    private String wicketType;
    private Long fielderId;
    private Boolean isBoundary;
    private Boolean isSix;
    private LocalDateTime createdAt;
    
    // Constructors
    public BallDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOverId() {
        return overId;
    }
    
    public void setOverId(Long overId) {
        this.overId = overId;
    }
    
    public Long getInningsId() {
        return inningsId;
    }
    
    public void setInningsId(Long inningsId) {
        this.inningsId = inningsId;
    }
    
    public Integer getBallNumber() {
        return ballNumber;
    }
    
    public void setBallNumber(Integer ballNumber) {
        this.ballNumber = ballNumber;
    }
    
    public Long getBatsmanId() {
        return batsmanId;
    }
    
    public void setBatsmanId(Long batsmanId) {
        this.batsmanId = batsmanId;
    }
    
    public Long getBowlerId() {
        return bowlerId;
    }
    
    public void setBowlerId(Long bowlerId) {
        this.bowlerId = bowlerId;
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
    
    public Long getFielderId() {
        return fielderId;
    }
    
    public void setFielderId(Long fielderId) {
        this.fielderId = fielderId;
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
