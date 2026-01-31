#!/usr/bin/env bash
set -euo pipefail

# Starts a local Postgres container for running migrations/tests.
# Environment variables (optional):
# POSTGRES_IMAGE (default: postgres:15)
# CONTAINER_NAME (default: pg-test)
# POSTGRES_USER (default: app)
# POSTGRES_PASSWORD (default: secret)
# POSTGRES_DB (default: cricketdb)
# HOST_PORT (default: 5432)

POSTGRES_IMAGE=${POSTGRES_IMAGE:-postgres:15}
CONTAINER_NAME=${CONTAINER_NAME:-pg-test}
POSTGRES_USER=${POSTGRES_USER:-app}
POSTGRES_PASSWORD=${POSTGRES_PASSWORD:-secret}
POSTGRES_DB=${POSTGRES_DB:-cricketdb}
HOST_PORT=${HOST_PORT:-5432}

echo "Starting Postgres container '$CONTAINER_NAME' (image: $POSTGRES_IMAGE) ..."

# If container exists and is running, just inform
if docker ps --format '{{.Names}}' | grep -qx "$CONTAINER_NAME"; then
  echo "Container '$CONTAINER_NAME' is already running."
else
  # If a stopped container with same name exists, remove it
  if docker ps -a --format '{{.Names}}' | grep -qx "$CONTAINER_NAME"; then
    echo "Removing existing stopped container '$CONTAINER_NAME'..."
    docker rm "$CONTAINER_NAME"
  fi

  docker run -d \
    --name "$CONTAINER_NAME" \
    -e POSTGRES_USER="$POSTGRES_USER" \
    -e POSTGRES_PASSWORD="$POSTGRES_PASSWORD" \
    -e POSTGRES_DB="$POSTGRES_DB" \
    -p "$HOST_PORT":5432 \
    "$POSTGRES_IMAGE"

  echo "Container started."
fi

# Wait for Postgres to be ready
echo "Waiting for Postgres to accept connections..."
RETRIES=30
until docker exec -i "$CONTAINER_NAME" pg_isready -U "$POSTGRES_USER" -d "$POSTGRES_DB" >/dev/null 2>&1; do
  ((RETRIES--)) || { echo "Postgres did not become ready in time." >&2; exit 1; }
  sleep 1
done

echo "Postgres is ready (container: $CONTAINER_NAME, db: $POSTGRES_DB, user: $POSTGRES_USER)."

echo "To stop: ./scripts/stop-postgres.sh"
