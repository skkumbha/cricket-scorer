-- Player Score Table
CREATE TABLE player_score (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    player_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    innings_id INTEGER NOT NULL,
    runs_scored INTEGER DEFAULT 0,
    runs_given INTEGER DEFAULT 0,
    balls INTEGER DEFAULT 0,
    wickets_taken INTEGER DEFAULT 0,
    sixes INTEGER DEFAULT 0,
    fours INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (innings_id) REFERENCES innings(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    UNIQUE (match_id, team_id, innings_id, player_id)
);