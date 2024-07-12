#!/usr/bin/env bash
set -e
cd app
mvn clean
mvn package
docker build -t bank-app .
cd ..
docker-compose -f docker-compose-local.yml up

