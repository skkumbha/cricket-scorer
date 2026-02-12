#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR=/Users/sreeramkumbham/IdeaProjects/cricket-scorer
DOCKERFILE=$PROJECT_DIR/Dockerfile-local
JAR_PATH=$PROJECT_DIR/target/cricket-scorer-1.0.0.jar
IMAGE_NAME=cricket-scorer-app-server:latest
CONTAINER_NAME=cricket-scorer-app-server

# stop/remove existing container (ignore if not present)
docker stop $CONTAINER_NAME || true
docker rm $CONTAINER_NAME || true

# ensure JAR exists
if [ ! -f "$JAR_PATH" ]; then
  echo "Jar not found at $JAR_PATH — building with Maven..."
  (cd "$PROJECT_DIR" && mvn -DskipTests package)
fi

# build image using project root as build context so COPY target/... works
docker build -t $IMAGE_NAME -f "$DOCKERFILE" "$PROJECT_DIR"

# run container
docker run \
  --name $CONTAINER_NAME \
  -p 8080:8080 \
  -p 5005:5005 \
  -d $IMAGE_NAME