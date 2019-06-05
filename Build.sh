#!/usr/bin/env bash

rootDir=${PWD}

if [[ -n $1 ]]
then
    rootDir=$1
fi

cd ${rootDir}

echo "Building directory ${rootDir}"

echo "Building database"
docker build -f DockerfilePg -t postgres:local .
echo "Done building database"

echo "Building boot application"
./gradlew clean build

docker build . --build-arg builddir=build/libs --build-arg appname=saas-challenge --build-arg version=0.1.0-RELEASE -t saas-challenge
echo "Done building boot application"