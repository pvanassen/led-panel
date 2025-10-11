#!/bin/sh
docker buildx build . --platform linux/amd64,linux/arm64 -t pvanassen.nl/led/animation-rainbow:latest --push --progress=plain