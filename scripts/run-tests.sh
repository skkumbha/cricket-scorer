#!/usr/bin/env bash
set -euo pipefail

# Runs basic SQL smoke tests against the local Postgres container.
# Environment variables (optional):
# CONTAINER_NAME (default: pg-test)
# POSTGRES_USER (default: app)
# POSTGRES_DB (default: cricketdb)

CONTAINER_NAME=${CONTAINER_NAME:-pg-test}
POSTGRES_USER=${POSTGRES_USER:-app}
POSTGRES_DB=${POSTGRES_DB:-cricketdb}

echo "Running smoke tests against container '$CONTAINER_NAME' (db: $POSTGRES_DB)..."

# 1) List tables (should show the tables created by migration)
echo "\n>>> Listing tables:"
docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "\dt"

# 2) Describe teams table
echo "\n>>> Describe teams table:"
docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "\d+ teams"

# 3) Insert a test team and return the id and created_at
echo "\n>>> Insert test team and return id + created_at:"
docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "INSERT INTO teams (name, short_name, logo_url) VALUES ('Local Test','LT','http://example.com') RETURNING id, created_at;"

# 4) Insert a test player and return id
echo "\n>>> Insert test player and return id:"
docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "INSERT INTO players (full_name, jersey_number, role) VALUES ('Test Player', 10, 'batter') RETURNING id;"

# 5) Clean up the inserted test rows (optional)
echo "\n>>> Cleaning up test rows..."
docker exec -i "$CONTAINER_NAME" psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "DELETE FROM match_awards WHERE award_description LIKE '%Local Test%'; DELETE FROM teams WHERE name='Local Test'; DELETE FROM players WHERE full_name='Test Player';"

echo "\nSmoke tests completed successfully."
