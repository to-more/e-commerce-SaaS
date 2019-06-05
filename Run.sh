#!/usr/bin/env bash

echo "Start database"
docker run -d --net=host --name postgres-local -e POSTGRES_PASSWORD=secretpassword postgres:local
echo "Done start database"
sleep 5

echo "Start boot application"
docker run -d --name saas-challenge --net=host -m=512m saas-challenge
echo "Done boot application"