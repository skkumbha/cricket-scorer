#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="${IMAGE_NAME:-cricket-scorer-app-server}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
CONTAINER_NAME="${CONTAINER_NAME:-cricket-scorer-app-server}"
HOST_PORT="${HOST_PORT:-8080}"
CONTAINER_PORT="${CONTAINER_PORT:-8080}"
DEBUG_PORT="${DEBUG_PORT:-5005}"

# Determine script directory and project root (assumes script is in <project>/scripts)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

# Ensure docker is available
if ! command -v docker >/dev/null 2>&1; then
  echo "ERROR: docker is not installed or not on PATH" >&2
  exit 1
fi

# Build the image from the Dockerfile at project root

echo "Building image ${IMAGE_NAME}:${IMAGE_TAG} from Dockerfile in ${PROJECT_ROOT}..."
docker build -t "${IMAGE_NAME}:${IMAGE_TAG}" -f "${PROJECT_ROOT}/Dockerfile" "${PROJECT_ROOT}"


# Remove any existing container with the same name
if docker ps -a --format '{{.Names}}' | grep -qx "${CONTAINER_NAME}"; then
  echo "Removing existing container ${CONTAINER_NAME}..."
  docker stop "${CONTAINER_NAME}" >/dev/null || true
  docker rm -f "${CONTAINER_NAME}" >/dev/null || true
fi

# Run the container detached
echo "Running container ${CONTAINER_NAME} (host ${HOST_PORT} -> container ${CONTAINER_PORT})..."
docker run -d \
  --name "${CONTAINER_NAME}" \
  -p "${HOST_PORT}:${CONTAINER_PORT}" \
  -p "${DEBUG_PORT}:${DEBUG_PORT}" \
  "${IMAGE_NAME}:${IMAGE_TAG}"

echo "Container ${CONTAINER_NAME} started from image ${IMAGE_NAME}:${IMAGE_TAG}."
echo "To watch logs: docker logs -f ${CONTAINER_NAME}"
