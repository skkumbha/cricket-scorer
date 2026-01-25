package com.cricket.scorer.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class PlayerDTO {
    
    private Long id;
    
    @NotBlank(message = "Player full name is required")
    private String fullName;
    
    private Integer jerseyNumber;
    private String role;
    private LocalDateTime createdAt;
    
    // Constructors
    public PlayerDTO() {
    }
    
    public PlayerDTO(Long id, String fullName, Integer jerseyNumber, String role, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.jerseyNumber = jerseyNumber;
        this.role = role;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
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
}
