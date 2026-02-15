package com.cricket.scorer.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MatchTeamDTO {

    private Long id;
    private String name;
    private String shortName;
    private String logoUrl;
    private LocalDateTime createdAt;
    private List<MatchTeamPlayerDTO> players;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }
    public void setPlayers(List<MatchTeamPlayerDTO> players) {
        this.players = players;
    }
    public List<MatchTeamPlayerDTO> getPlayers() {
        return this.players;
    }

}
