
PROJECT_DIR=/Users/sreeramkumbham/IdeaProjects/cricket-scorer
DOCKERFILE=$PROJECT_DIR/Dockerfile
IMAGE_NAME=cricket-score-app
IMAGE_TAG=latest
DOCKER_HUB_USERNAME=sreeramkumbham

docker buildx build \
 --platform linux/amd64,linux/arm64 \
 -t $DOCKER_HUB_USERNAME/$IMAGE_NAME:$IMAGE_TAG \
 --push \
 -f "$DOCKERFILE" "$PROJECT_DIR"




