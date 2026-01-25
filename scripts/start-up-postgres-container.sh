#!/usr/bin/env bash
set -euo pipefail
# Starts a temporary Postgres container for local testing

# Initialize Container Names, Credentials, DB Name, and Port
CONTAINER_NAME="postgres-cricket-scorer"
POSTGRES_USER="postgres"
POSTGRES_PASSWORD="dbP@ssW0rd"
POSTGRES_DB="cricket_scorer_db"
HOST_PORT=5432

if docker ps -a --format '{{.Names}}' | grep -qx "$CONTAINER_NAME"; then
  docker stop "$CONTAINER_NAME"
  docker rm "$CONTAINER_NAME"
fi

docker run -d \
  --name "$CONTAINER_NAME" \
  -e POSTGRES_USER="$POSTGRES_USER" \
  -e POSTGRES_PASSWORD="$POSTGRES_PASSWORD" \
  -e POSTGRES_DB="$POSTGRES_DB" \
  -p "$HOST_PORT":5432 \
  postgres:15

# Wait for Postgres to be ready
echo "Waiting for Postgres to accept connections..."


for i in {1..30}; do
  if docker exec "$CONTAINER_NAME" pg_isready -U "$POSTGRES_USER" -d "$POSTGRES_DB" > /dev/null 2>&1;
  then
    break
  fi
  sleep 1
done

if [ $i -eq 30 ]; then
  echo "Postgres did not become ready in time." >&2
  exit 1
  else
  echo "Postgres is ready (container: $CONTAINER_NAME, db: $POSTGRES_DB, user: $POSTGRES_USER)."
fi