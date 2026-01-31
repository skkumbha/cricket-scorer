#!/usr/bin/env bash
set -euo pipefail

# Applies Flyway SQL migration file(s) to the local Postgres container started by start-postgres.sh
# Environment variables (optional):
# CONTAINER_NAME (default: pg-test)
# POSTGRES_USER (default: app)
# POSTGRES_DB (default: cricketdb)
# MIGRATION_FILE (default: src/main/resources/db/migration/V1__initialize_schema.sql)

CONTAINER_NAME=${CONTAINER_NAME:-pg-test}
POSTGRES_USER=${POSTGRES_USER:-app}
POSTGRES_DB=${POSTGRES_DB:-cricketdb}
MIGRATION_FILE=${MIGRATION_FILE:-src/main/resources/db/migration/V1__initialize_schema.sql}

if [ ! -f "$MIGRATION_FILE" ]; then
  echo "Migration file not found: $MIGRATION_FILE" >&2
  exit 1
fi

# Drop tables if they exist (safe for repeated runs). Order matters (children first).
TABLES=(
  match_awards
  fixtures
  scorer_requests
  match_access
  balls
  overs
  innings
  match_players
  match_teams
  team_players
  match_players
  players
  matches
  tournaments
  teams
)

# Generate drop statements
DROP_SQL="BEGIN;\n"
for t in "${TABLES[@]}"; do
  DROP_SQL+="DROP TABLE IF EXISTS \"$t\" CASCADE;\n"
done
DROP_SQL+="COMMIT;\n"

echo "Dropping existing tables (if any) in container $CONTAINER_NAME..."
# Execute drop statements
echo -e "$DROP_SQL" | docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"

echo "Applying migration file: $MIGRATION_FILE"
docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" < "$MIGRATION_FILE"

echo "Migration applied successfully."

