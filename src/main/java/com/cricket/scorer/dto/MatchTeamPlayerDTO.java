package com.cricket.scorer.dto;

import java.time.LocalDateTime;

public class MatchTeamPlayerDTO {

    private Long id;
    private String fullName;
    private Integer jerseyNumber;
    private String role;
    private LocalDateTime createdAt;

    public MatchTeamPlayerDTO() {
    }

    public MatchTeamPlayerDTO(Long id, String fullName, Integer jerseyNumber,
                              String role, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.jerseyNumber = jerseyNumber;
        this.role = role;
        this.createdAt = createdAt;

    }

    public Long getId(){
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Integer getJerseyNumber() {
        return this.jerseyNumber;
    }
    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }
    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
