-- DDL: Cricket Scoring App Schema

CREATE TABLE teams (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  short_name TEXT,
  logo_url TEXT,
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE players (
  id SERIAL PRIMARY KEY,
  full_name TEXT NOT NULL,
  jersey_number INT,
  role TEXT,
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE team_players (
  team_id INT REFERENCES teams(id) ON DELETE CASCADE,
  player_id INT REFERENCES players(id) ON DELETE CASCADE,
  PRIMARY KEY (team_id, player_id)
);

CREATE TABLE matches (
  id UUID PRIMARY KEY,
  match_name TEXT,
  match_type TEXT DEFAULT 'Friendly',
  overs INT NOT NULL,
  balls_per_over INT DEFAULT 6,
  location TEXT,
  start_time TIMESTAMP DEFAULT NOW(),
  status TEXT DEFAULT 'setup'
);

CREATE TABLE match_teams (
  id SERIAL PRIMARY KEY,
  match_id UUID REFERENCES matches(id) ON DELETE CASCADE,
  team_id INT REFERENCES teams(id),
  innings_number INT
);

CREATE TABLE match_players (
  id SERIAL PRIMARY KEY,
  match_id UUID REFERENCES matches(id) ON DELETE CASCADE,
  team_id INT REFERENCES teams(id),
  player_id INT REFERENCES players(id),
  is_playing BOOLEAN DEFAULT TRUE
);

CREATE TABLE innings (
  id SERIAL PRIMARY KEY,
  match_id UUID REFERENCES matches(id) ON DELETE CASCADE,
  batting_team_id INT REFERENCES teams(id),
  bowling_team_id INT REFERENCES teams(id),
  innings_number INT,
  total_runs INT DEFAULT 0,
  wickets INT DEFAULT 0,
  overs_completed DECIMAL(4,1) DEFAULT 0
);

CREATE TABLE overs (
  id SERIAL PRIMARY KEY,
  innings_id INT REFERENCES innings(id) ON DELETE CASCADE,
  over_number INT,
  bowler_id INT REFERENCES players(id)
);

CREATE TABLE balls (
  id SERIAL PRIMARY KEY,
  over_id INT REFERENCES overs(id) ON DELETE CASCADE,
  ball_number INT,
  striker_id INT REFERENCES players(id),
  non_striker_id INT REFERENCES players(id),
  bowler_id INT REFERENCES players(id),
  runs_off_bat INT DEFAULT 0,
  extras INT DEFAULT 0,
  extra_type TEXT,
  is_wicket BOOLEAN DEFAULT FALSE,
  wicket_type TEXT,
  dismissed_player_id INT REFERENCES players(id)
);

CREATE TABLE match_access (
  id SERIAL PRIMARY KEY,
  match_id UUID REFERENCES matches(id) ON DELETE CASCADE,
  access_type TEXT,
  access_token TEXT UNIQUE NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE scorer_requests (
  id SERIAL PRIMARY KEY,
  match_id UUID REFERENCES matches(id) ON DELETE CASCADE,
  requested_by_token TEXT,
  status TEXT DEFAULT 'pending',
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE tournaments (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  season TEXT,
  start_date DATE,
  end_date DATE
);

CREATE TABLE fixtures (
  id SERIAL PRIMARY KEY,
  tournament_id INT REFERENCES tournaments(id),
  match_id UUID REFERENCES matches(id),
  scheduled_time TIMESTAMP
);

CREATE TABLE match_awards (
  id SERIAL PRIMARY KEY,
  match_id UUID REFERENCES matches(id),
  player_id INT REFERENCES players(id),
  award_type TEXT,
  notes TEXT
);

-- New table appended only
CREATE TABLE team_authorized_users (
  id SERIAL PRIMARY KEY,
  team_id INT REFERENCES teams(id) ON DELETE CASCADE,
  user_name TEXT NOT NULL,
  role TEXT NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT NOW()
);
