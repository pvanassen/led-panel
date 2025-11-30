#!/bin/sh
docker buildx build . --platform linux/arm/v7 -t pvanassen.nl/led/led-sensor:latest --pull --progress=plain
