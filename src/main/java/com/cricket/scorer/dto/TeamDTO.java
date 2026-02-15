package com.cricket.scorer.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public class TeamDTO {
    
    private Long id;
    private String name;
    private String shortName;
    private String logoUrl;
    private LocalDateTime createdAt;
    private List<PlayerDTO> players;
    private List<Long> playerIds;
    
    // Constructors
    public TeamDTO() {
    }
    
    public TeamDTO(Long id, String name, String shortName, String logoUrl, LocalDateTime createdAt, List<PlayerDTO> players) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
        this.createdAt = createdAt;
        this.players = players;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getLogoUrl() {
        return logoUrl;
    }
    
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public List<PlayerDTO> getPlayers() {
        return players;
    }
    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
    public List<Long> getPlayerIds() {
        return playerIds;
    }
    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }
}
