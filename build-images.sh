#!/usr/bin/env bash

declare -a mvn_projects=("orders-api" "users-api")
for mvn_project in "${mvn_projects[@]}"
do
  cd $mvn_project
  aws --version
  echo Docker build and tagging started on `date`
  docker build -t $mvn_project:latest .
  echo Docker build and tagging completed on `date`
  cd ..
done
