-- Teams table
CREATE TABLE teams (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(50),
    logo_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Players table
CREATE TABLE players (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    jersey_number INTEGER,
    role VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Team Players mapping table
CREATE TABLE team_players (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    team_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    UNIQUE (team_id, player_id)
);

-- Tournaments table
CREATE TABLE tournaments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(50),
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Matches table
CREATE TABLE matches (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_name VARCHAR(255) NOT NULL,
    match_type VARCHAR(50),
    location VARCHAR(255),
    start_time TIMESTAMP,
    status VARCHAR(50) DEFAULT 'SCHEDULED',
    tournament_id BIGINT,
    toss_winner_team_id BIGINT,
    toss_decision VARCHAR(20),
    winner_team_id BIGINT,
    result_type VARCHAR(50),
    result_margin VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE SET NULL
);

-- Match Teams mapping table
CREATE TABLE match_teams (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    team_type VARCHAR(20),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
);

-- Match Players mapping table
CREATE TABLE match_players (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    playing_role VARCHAR(50),
    is_captain BOOLEAN DEFAULT FALSE,
    is_wicket_keeper BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
);

-- Innings table
CREATE TABLE innings (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    innings_number INTEGER NOT NULL,
    batting_team_id BIGINT NOT NULL,
    bowling_team_id BIGINT NOT NULL,
    total_runs INTEGER DEFAULT 0,
    total_wickets INTEGER DEFAULT 0,
    total_overs DECIMAL(4,1) DEFAULT 0.0,
    extras INTEGER DEFAULT 0,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (batting_team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (bowling_team_id) REFERENCES teams(id) ON DELETE CASCADE
);

-- Overs table
CREATE TABLE overs (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    innings_id BIGINT NOT NULL,
    over_number INTEGER NOT NULL,
    bowler_id BIGINT NOT NULL,
    runs_conceded INTEGER DEFAULT 0,
    wickets_taken INTEGER DEFAULT 0,
    maiden BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (innings_id) REFERENCES innings(id) ON DELETE CASCADE,
    FOREIGN KEY (bowler_id) REFERENCES players(id) ON DELETE CASCADE
);

-- Balls table
CREATE TABLE balls (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    over_id BIGINT NOT NULL,
    innings_id BIGINT NOT NULL,
    ball_number INTEGER NOT NULL,
    batsman_id BIGINT NOT NULL,
    bowler_id BIGINT NOT NULL,
    runs_scored INTEGER DEFAULT 0,
    extras INTEGER DEFAULT 0,
    extra_type VARCHAR(20),
    is_wicket BOOLEAN DEFAULT FALSE,
    wicket_type VARCHAR(50),
    fielder_id BIGINT,
    is_boundary BOOLEAN DEFAULT FALSE,
    is_six BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (over_id) REFERENCES overs(id) ON DELETE CASCADE,
    FOREIGN KEY (innings_id) REFERENCES innings(id) ON DELETE CASCADE,
    FOREIGN KEY (batsman_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (bowler_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (fielder_id) REFERENCES players(id) ON DELETE SET NULL
);

-- Match Access table
CREATE TABLE match_access (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    access_level VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    UNIQUE (match_id, user_id)
);

-- Scorer Requests table
CREATE TABLE scorer_requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    request_status VARCHAR(50) DEFAULT 'PENDING',
    requested_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    resolved_at TIMESTAMP WITH TIME ZONE,
    resolved_by VARCHAR(255),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE
);

-- Fixtures table
CREATE TABLE fixtures (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    match_id BIGINT,
    fixture_name VARCHAR(255),
    scheduled_date TIMESTAMP,
    venue VARCHAR(255),
    match_number INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE SET NULL
);

-- Match Awards table
CREATE TABLE match_awards (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    match_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    award_type VARCHAR(100) NOT NULL,
    award_description VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);
