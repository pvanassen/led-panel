#!/bin/sh
docker buildx build . --platform linux/arm64 -t pvanassen.nl/led/led-sensor:latest --pull --push --progress=plain