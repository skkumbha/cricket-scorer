# Cricket Scorer

A Spring Boot application that exposes REST API endpoints for cricket scoring model creation and management in a database.

## Features

- **Team Management**: Create, read, update, and delete teams
- **Player Management**: Manage players with team associations
- **Match Management**: Track cricket matches between teams
- **H2 In-Memory Database**: Embedded database for quick setup
- **JPA/Hibernate**: ORM for seamless database operations

## Technology Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Database Console

Access the H2 console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:cricketdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Teams

- `GET /api/teams` - Get all teams
- `GET /api/teams/{id}` - Get team by ID
- `POST /api/teams` - Create a new team
- `PUT /api/teams/{id}` - Update a team
- `DELETE /api/teams/{id}` - Delete a team
- `GET /api/teams/search?name={name}` - Search teams by name

**Example Request:**
```bash
curl -X POST http://localhost:8080/api/teams \
  -H "Content-Type: application/json" \
  -d '{"name":"India","country":"India"}'
```

### Players

- `GET /api/players/{id}` - Get player by ID
- `POST /api/players` - Create a new player
- `PUT /api/players/{id}` - Update a player
- `DELETE /api/players/{id}` - Delete a player
- `GET /api/players/team/{teamId}` - Get players by team
- `GET /api/players/role/{role}` - Get players by role
- `GET /api/players/search?name={name}` - Search players by name

**Example Request:**
```bash
curl -X POST http://localhost:8080/api/players \
  -H "Content-Type: application/json" \
  -d '{"name":"Virat Kohli","teamId":1,"role":"Batsman","jerseyNumber":18}'
```

### Matches

- `GET /api/matches` - Get all matches
- `GET /api/matches/{id}` - Get match by ID
- `POST /api/matches` - Create a new match
- `PUT /api/matches/{id}` - Update a match
- `DELETE /api/matches/{id}` - Delete a match
- `GET /api/matches/status/{status}` - Get matches by status
- `GET /api/matches/team/{teamId}` - Get matches by team

**Example Request:**
```bash
curl -X POST http://localhost:8080/api/matches \
  -H "Content-Type: application/json" \
  -d '{"name":"India vs Australia","team1Id":1,"team2Id":2,"venue":"Mumbai","matchDate":"2026-01-20T10:00:00"}'
```

## Models

### Team
- `id` (Long): Unique identifier
- `name` (String): Team name (required)
- `country` (String): Country name
- `createdAt` (LocalDateTime): Creation timestamp
- `updatedAt` (LocalDateTime): Update timestamp

### Player
- `id` (Long): Unique identifier
- `name` (String): Player name (required)
- `teamId` (Long): Associated team ID (required)
- `role` (String): Player role (Batsman, Bowler, All-rounder, Wicket-keeper)
- `jerseyNumber` (Integer): Jersey number
- `createdAt` (LocalDateTime): Creation timestamp
- `updatedAt` (LocalDateTime): Update timestamp

### Match
- `id` (Long): Unique identifier
- `name` (String): Match name (required)
- `team1Id` (Long): First team ID (required)
- `team2Id` (Long): Second team ID (required)
- `venue` (String): Match venue
- `matchDate` (LocalDateTime): Match date and time
- `status` (String): Match status (Scheduled, In Progress, Completed, Cancelled)
- `winnerTeamId` (Long): Winner team ID
- `createdAt` (LocalDateTime): Creation timestamp
- `updatedAt` (LocalDateTime): Update timestamp