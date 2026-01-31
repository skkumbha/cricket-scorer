package com.cricket.scorer.dto;

public class TeamPlayerDTO {

    private TeamDTO team;
    private PlayerDTO player;

    public TeamPlayerDTO() {
    }
    public TeamPlayerDTO(TeamDTO team, PlayerDTO player) {
        this.team = team;
        this.player = player;
    }
    public TeamDTO getTeam() {
        return team;
    }
    public void setTeam(TeamDTO team) {
        this.team = team;
    }
    public PlayerDTO getPlayer() {
        return player;
    }
    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
}
