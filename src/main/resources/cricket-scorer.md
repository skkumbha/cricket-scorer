# Cricket Scoring App — Data Model Diagram (ERD)

> Mermaid ER diagram generated from the provided DDL. The diagram below is cleaned up for correct Mermaid syntax and readability. After the diagram there is a short legend and per-entity field tables for quick reference.

```mermaid
erDiagram
  TEAMS {
    INT id PK
    TEXT name
    TEXT short_name
    TEXT logo_url
    TIMESTAMP created_at
  }

  PLAYERS {
    INT id PK
    TEXT full_name
    INT jersey_number
    TEXT role
    TIMESTAMP created_at
  }

  TEAM_PLAYERS {
    INT team_id PK FK
    INT player_id PK FK
  }

  MATCHES {
    UUID id PK
    TEXT match_name
    TEXT match_type
    INT overs
    INT balls_per_over
    TEXT location
    TIMESTAMP start_time
    TEXT status
  }

  MATCH_TEAMS {
    INT id PK
    UUID match_id FK
    INT team_id FK
    INT innings_number
  }

  MATCH_PLAYERS {
    INT id PK
    UUID match_id FK
    INT team_id FK
    INT player_id FK
    BOOLEAN is_playing
  }

  INNINGS {
    INT id PK
    UUID match_id FK
    INT batting_team_id FK
    INT bowling_team_id FK
    INT innings_number
    INT total_runs
    INT wickets
    DECIMAL overs_completed
  }

  OVERS {
    INT id PK
    INT innings_id FK
    INT over_number
    INT bowler_id FK
  }

  BALLS {
    INT id PK
    INT over_id FK
    INT ball_number
    INT striker_id FK
    INT non_striker_id FK
    INT bowler_id FK
    INT runs_off_bat
    INT extras
    TEXT extra_type
    BOOLEAN is_wicket
    TEXT wicket_type
    INT dismissed_player_id FK
  }

  MATCH_ACCESS {
    INT id PK
    UUID match_id FK
    TEXT access_type
    TEXT access_token UK
    BOOLEAN is_active
    TIMESTAMP created_at
  }

  SCORER_REQUESTS {
    INT id PK
    UUID match_id FK
    TEXT requested_by_token
    TEXT status
    TIMESTAMP created_at
  }

  TOURNAMENTS {
    INT id PK
    TEXT name
    TEXT season
    DATE start_date
    DATE end_date
  }

  FIXTURES {
    INT id PK
    INT tournament_id FK
    UUID match_id FK
    TIMESTAMP scheduled_time
  }

  MATCH_AWARDS {
    INT id PK
    UUID match_id FK
    INT player_id FK
    TEXT award_type
    TEXT notes
  }

  %% Relationships (read as: LEFT_CARDINALITY relationship_symbol RIGHT_CARDINALITY)
  TEAMS ||--o{ TEAM_PLAYERS : "has"
  PLAYERS ||--o{ TEAM_PLAYERS : "belongs_to"

  MATCHES ||--o{ MATCH_TEAMS : "includes"
  TEAMS ||--o{ MATCH_TEAMS : "participates_as"

  MATCHES ||--o{ MATCH_PLAYERS : "selects"
  TEAMS ||--o{ MATCH_PLAYERS : "fields"
  PLAYERS ||--o{ MATCH_PLAYERS : "plays_in"

  MATCHES ||--o{ INNINGS : "has"
  TEAMS ||--o{ INNINGS : "bats"
  TEAMS ||--o{ INNINGS : "bowls"

  INNINGS ||--o{ OVERS : "contains"
  PLAYERS ||--o{ OVERS : "bowls"

  OVERS ||--o{ BALLS : "contains"
  PLAYERS ||--o{ BALLS : "striker"
  PLAYERS ||--o{ BALLS : "non_striker"
  PLAYERS ||--o{ BALLS : "bowler"
  PLAYERS ||--o{ BALLS : "dismissed"

  MATCHES ||--o{ MATCH_ACCESS : "access_links"
  MATCHES ||--o{ SCORER_REQUESTS : "scorer_requests"

  TOURNAMENTS ||--o{ FIXTURES : "schedules"
  MATCHES ||--o{ FIXTURES : "referenced_match"

  MATCHES ||--o{ MATCH_AWARDS : "awards"
  PLAYERS ||--o{ MATCH_AWARDS : "receives"
```

---

Legend

- PK = Primary Key
- FK = Foreign Key
- UK = Unique Key
- INT, TEXT, UUID, TIMESTAMP, DATE, DECIMAL = suggested data types
- Relationships: || = one, }| = many (Mermaid ER notation uses symbols like ||--o{)

---

Entity reference (quick tables)

TEAMS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| name | TEXT | |
| short_name | TEXT | |
| logo_url | TEXT | |
| created_at | TIMESTAMP | |

PLAYERS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| full_name | TEXT | |
| jersey_number | INT | |
| role | TEXT | e.g. batter, bowler, all-rounder |
| created_at | TIMESTAMP | |

TEAM_PLAYERS (join table)

| Column | Type | Notes |
|---|---:|---|
| team_id | INT | PK, FK -> TEAMS.id |
| player_id | INT | PK, FK -> PLAYERS.id |

MATCHES

| Column | Type | Notes |
|---|---:|---|
| id | UUID | PK |
| match_name | TEXT | |
| match_type | TEXT | e.g. T20, ODI |
| overs | INT | |
| balls_per_over | INT | |
| location | TEXT | |
| start_time | TIMESTAMP | |
| status | TEXT | e.g. scheduled, in_progress, finished |

MATCH_TEAMS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| match_id | UUID | FK -> MATCHES.id |
| team_id | INT | FK -> TEAMS.id |
| innings_number | INT | |

MATCH_PLAYERS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| match_id | UUID | FK -> MATCHES.id |
| team_id | INT | FK -> TEAMS.id |
| player_id | INT | FK -> PLAYERS.id |
| is_playing | BOOLEAN | whether selected in playing XI |

INNINGS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| match_id | UUID | FK -> MATCHES.id |
| batting_team_id | INT | FK -> TEAMS.id |
| bowling_team_id | INT | FK -> TEAMS.id |
| innings_number | INT | |
| total_runs | INT | |
| wickets | INT | |
| overs_completed | DECIMAL | e.g. 10.4 means 10 overs and 4 balls |

OVERS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| innings_id | INT | FK -> INNINGS.id |
| over_number | INT | |
| bowler_id | INT | FK -> PLAYERS.id |

BALLS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| over_id | INT | FK -> OVERS.id |
| ball_number | INT | 1..balls_per_over |
| striker_id | INT | FK -> PLAYERS.id |
| non_striker_id | INT | FK -> PLAYERS.id |
| bowler_id | INT | FK -> PLAYERS.id |
| runs_off_bat | INT | |
| extras | INT | |
| extra_type | TEXT | e.g. wide, no_ball, bye |
| is_wicket | BOOLEAN | |
| wicket_type | TEXT | e.g. bowled, lbw, caught |
| dismissed_player_id | INT | FK -> PLAYERS.id |

MATCH_ACCESS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| match_id | UUID | FK -> MATCHES.id |
| access_type | TEXT | e.g. public, token |
| access_token | TEXT | UK |
| is_active | BOOLEAN | |
| created_at | TIMESTAMP | |

SCORER_REQUESTS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| match_id | UUID | FK -> MATCHES.id |
| requested_by_token | TEXT | token of requester |
| status | TEXT | e.g. pending, approved |
| created_at | TIMESTAMP | |

TOURNAMENTS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| name | TEXT | |
| season | TEXT | |
| start_date | DATE | |
| end_date | DATE | |

FIXTURES

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| tournament_id | INT | FK -> TOURNAMENTS.id |
| match_id | UUID | FK -> MATCHES.id |
| scheduled_time | TIMESTAMP | |

MATCH_AWARDS

| Column | Type | Notes |
|---|---:|---|
| id | INT | PK |
| match_id | UUID | FK -> MATCHES.id |
| player_id | INT | FK -> PLAYERS.id |
| award_type | TEXT | e.g. "Player of the Match" |
| notes | TEXT | |

---

Notes

- I removed commas from inline field lines and ensured each field is on its own line so Mermaid parses the ERD correctly. If you view this file in a markdown preview that supports Mermaid (for example: VS Code with Mermaid plugin or Obsidian/GitHub when enabled), the diagram should render correctly.
- If you'd like, I can also generate a PNG/SVG export of the Mermaid diagram and add it to the repo for readers who don't have Mermaid rendering in their Markdown viewer.
