# Cricket Scorer - Models and DAOs

A Java-based cricket scoring application with JPA/Hibernate models and Data Access Objects (DAOs) for managing cricket matches, teams, players, and scoring data.

## Features

- **Complete Entity Models**: All cricket scoring entities implemented with JPA annotations
- **DAO Pattern**: Generic DAO interface with specific implementations for each entity
- **Relationships**: Properly mapped relationships between entities (One-to-Many, Many-to-One, Many-to-Many)
- **Database Support**: PostgreSQL database with Hibernate ORM
- **Cascade Operations**: Appropriate cascade rules for data integrity

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── cricket/
│   │           └── scorer/
│   │               ├── model/           # Entity classes
│   │               │   ├── Team.java
│   │               │   ├── Player.java
│   │               │   ├── Match.java
│   │               │   ├── Innings.java
│   │               │   ├── Over.java
│   │               │   ├── Ball.java
│   │               │   └── ...
│   │               └── dao/             # Data Access Objects
│   │                   ├── GenericDAO.java
│   │                   ├── AbstractDAO.java
│   │                   ├── TeamDAO.java
│   │                   ├── PlayerDAO.java
│   │                   └── ...
│   └── resources/
│       └── META-INF/
│           └── persistence.xml          # JPA configuration
└── test/
    └── java/
        └── com/
            └── cricket/
                └── scorer/              # Test classes
```

## Entity Models

### Core Entities

1. **Team** - Cricket teams with name, short name, and logo
2. **Player** - Players with full name, jersey number, and role
3. **TeamPlayer** - Association between teams and players
4. **Match** - Match details including type, location, and status
5. **MatchTeam** - Teams participating in a match
6. **MatchPlayer** - Players selected for a match
7. **Innings** - Innings data with runs, wickets, and overs
8. **Over** - Individual overs with bowler information
9. **Ball** - Ball-by-ball scoring data
10. **MatchAccess** - Access tokens for match scoring
11. **ScorerRequest** - Requests to become a scorer
12. **Tournament** - Tournament information
13. **Fixture** - Match fixtures in tournaments
14. **MatchAward** - Awards given to players

## DAO Classes

Each entity has a corresponding DAO with:
- Standard CRUD operations (Create, Read, Update, Delete)
- Custom query methods for specific use cases
- Transaction management support

### Generic DAO Methods

```java
T save(T entity)
Optional<T> findById(ID id)
List<T> findAll()
void delete(T entity)
void deleteById(ID id)
boolean existsById(ID id)
long count()
```

### Example Custom Methods

**TeamDAO**:
- `findByNameContaining(String name)`
- `findByShortName(String shortName)`

**PlayerDAO**:
- `findByRole(String role)`
- `findByTeamId(Integer teamId)`

**MatchDAO**:
- `findByStatus(String status)`
- `findByDateRange(LocalDateTime start, LocalDateTime end)`
- `findByTeamId(Integer teamId)`

**InningsDAO**:
- `findByMatchId(UUID matchId)`
- `findByBattingTeamId(Integer teamId)`

## Setup and Configuration

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- PostgreSQL 10+

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE cricket_scorer;
```

2. Run the DDL script to create tables (provided in the problem statement)

3. Update database credentials in `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/cricket_scorer"/>
<property name="javax.persistence.jdbc.user" value="your_username"/>
<property name="javax.persistence.jdbc.password" value="your_password"/>
```

### Build the Project

```bash
mvn clean install
```

## Usage Examples

### Creating a Team

```java
import com.cricket.scorer.model.Team;
import com.cricket.scorer.dao.TeamDAO;
import com.cricket.scorer.dao.EntityManagerUtil;

EntityManager em = EntityManagerUtil.getEntityManager();
TeamDAO teamDAO = new TeamDAO();
teamDAO.setEntityManager(em);

em.getTransaction().begin();

Team team = new Team("Mumbai Indians");
team.setShortName("MI");
team.setLogoUrl("https://example.com/mi-logo.png");

teamDAO.save(team);

em.getTransaction().commit();
em.close();
```

### Creating a Player

```java
import com.cricket.scorer.model.Player;
import com.cricket.scorer.dao.PlayerDAO;

EntityManager em = EntityManagerUtil.getEntityManager();
PlayerDAO playerDAO = new PlayerDAO();
playerDAO.setEntityManager(em);

em.getTransaction().begin();

Player player = new Player("Virat Kohli");
player.setJerseyNumber(18);
player.setRole("Batsman");

playerDAO.save(player);

em.getTransaction().commit();
em.close();
```

### Creating a Match

```java
import com.cricket.scorer.model.Match;
import com.cricket.scorer.dao.MatchDAO;
import java.util.UUID;

EntityManager em = EntityManagerUtil.getEntityManager();
MatchDAO matchDAO = new MatchDAO();
matchDAO.setEntityManager(em);

em.getTransaction().begin();

Match match = new Match(20); // 20 overs
match.setId(UUID.randomUUID());
match.setMatchName("IPL Final 2024");
match.setMatchType("Tournament");
match.setLocation("Wankhede Stadium");
match.setStatus("setup");

matchDAO.save(match);

em.getTransaction().commit();
em.close();
```

### Querying Data

```java
// Find all teams
List<Team> teams = teamDAO.findAll();

// Find teams by name
List<Team> searchResults = teamDAO.findByNameContaining("Mumbai");

// Find players by role
List<Player> batsmen = playerDAO.findByRole("Batsman");

// Find matches by status
List<Match> activeMatches = matchDAO.findByStatus("in_progress");

// Find innings for a match
List<Innings> innings = inningsDAO.findByMatchId(matchId);
```

### Transaction Management

```java
EntityManager em = EntityManagerUtil.getEntityManager();
try {
    em.getTransaction().begin();
    
    // Perform multiple operations
    teamDAO.save(team);
    playerDAO.save(player);
    
    em.getTransaction().commit();
} catch (Exception e) {
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }
    throw e;
} finally {
    em.close();
}
```

## Database Schema

The models are designed to match the following database schema:

- **teams**: Team information
- **players**: Player details
- **team_players**: Many-to-many relationship between teams and players
- **matches**: Match information with UUID primary key
- **match_teams**: Teams playing in a match
- **match_players**: Players selected for a match
- **innings**: Innings data for each team
- **overs**: Over-by-over data
- **balls**: Ball-by-ball scoring
- **match_access**: Access control for matches
- **scorer_requests**: Scorer permission requests
- **tournaments**: Tournament information
- **fixtures**: Match scheduling in tournaments
- **match_awards**: Player awards

## Key Features

### Proper Relationships
- Teams have many players through TeamPlayer association
- Matches have many innings, teams, and players
- Innings have many overs
- Overs have many balls
- Cascading deletes where appropriate

### Constraints
- Primary keys (auto-generated for most entities)
- Foreign key relationships
- NOT NULL constraints on required fields
- Default values where specified
- Unique constraints (e.g., access_token)

### JPA Features
- `@PrePersist` callbacks for timestamps
- Lazy loading for relationships
- Cascade operations
- Composite primary keys for association tables

## Contributing

When extending this project:
1. Follow the existing DAO pattern
2. Add appropriate indexes for query performance
3. Include JavaDoc comments
4. Write unit tests for new DAOs

## License

This project is part of a cricket scoring application.