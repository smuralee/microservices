#!/usr/bin/env bash

echo Initiate container images preparation...
declare -a mvn_projects=("orders-api" "users-api")
for mvn_project in "${mvn_projects[@]}"
do
  cd $mvn_project
  aws --version
  REPOSITORY_URI=$ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$mvn_project
  COMMIT_HASH=$(echo $CODEBUILD_RESOLVED_SOURCE_VERSION | cut -c 1-7)
  IMAGE_TAG=${COMMIT_HASH:=latest}
  aws ecr get-login-password | docker login --username AWS --password-stdin $REPOSITORY_URI
  docker build -t $REPOSITORY_URI:latest -t $REPOSITORY_URI:$IMAGE_TAG .
  echo Completed container image built and tagging on `date`
  docker push $REPOSITORY_URI:latest
  docker push $REPOSITORY_URI:$IMAGE_TAG
  echo Completed container image push on `date`
  sed -i 's@REPOSITORY_URI@'$REPOSITORY_URI'@g' taskdef.json
  sed -i 's@IMAGE_TAG@'$IMAGE_TAG'@g' taskdef.json
  sed -i 's@AWS_REGION@'$AWS_REGION'@g' taskdef.json
  sed -i 's@TASK_EXECUTION_ARN@'$TASK_EXECUTION_ARN'@g' taskdef.json
  mv taskdef.json ../${mvn_project}-taskdef.json
  mv appspec.yaml ../${mvn_project}-appspec.yaml
  echo Completed task definition and app spec updates on `date`
  cd ..
done
