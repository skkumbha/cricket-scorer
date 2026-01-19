-- Sample Data Inserts

INSERT INTO teams (name, short_name) VALUES
('Blue Warriors', 'BW'),
('Red Strikers', 'RS');

INSERT INTO players (full_name, jersey_number, role) VALUES
('Arjun Patel', 7, 'batsman'),
('Rahul Sharma', 10, 'bowler'),
('Vikram Singh', 18, 'allrounder'),
('Amit Verma', 1, 'wicketkeeper');

INSERT INTO team_players VALUES
(1,1),(1,2),(2,3),(2,4);

INSERT INTO matches (id, match_name, overs, location, status) VALUES
('11111111-1111-1111-1111-111111111111', 'Sunday Friendly', 20, 'Central Park', 'live');

INSERT INTO match_teams (match_id, team_id, innings_number) VALUES
('11111111-1111-1111-1111-111111111111', 1, 1),
('11111111-1111-1111-1111-111111111111', 2, 2);

INSERT INTO match_players (match_id, team_id, player_id) VALUES
('11111111-1111-1111-1111-111111111111',1,1),
('11111111-1111-1111-1111-111111111111',1,2),
('11111111-1111-1111-1111-111111111111',2,3),
('11111111-1111-1111-1111-111111111111',2,4);

INSERT INTO innings (match_id, batting_team_id, bowling_team_id, innings_number) VALUES
('11111111-1111-1111-1111-111111111111',1,2,1);

INSERT INTO match_awards (match_id, player_id, award_type, notes) VALUES
('11111111-1111-1111-1111-111111111111',1,'Man of the Match','Great opening innings');

-- New sample records appended only
INSERT INTO team_authorized_users (team_id, user_name, role) VALUES
(1, 'Coach Ravi', 'Coach'),
(1, 'Admin Suresh', 'Team Admin'),
(2, 'Manager Karan', 'Manager'),
(2, 'Umpire John', 'Umpire');
