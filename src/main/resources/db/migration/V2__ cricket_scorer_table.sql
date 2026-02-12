-- Score Table
CREATE TABLE score (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    innings_id INTEGER NOT NULL,
    runs INTEGER DEFAULT 0,
    wickets INTEGER DEFAULT 0,
    overs DECIMAL(4,1) DEFAULT 0.0,
    extras INTEGER DEFAULT 0,
    crr DECIMAL(5,2) DEFAULT 0.0,
    rrr DECIMAL(5,2) DEFAULT 0.0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (innings_id) REFERENCES innings(id) ON DELETE CASCADE,
    UNIQUE (match_id, team_id, innings_id)
);
