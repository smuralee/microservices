#!/usr/bin/env bash

declare -a service_names=("orders" "users")
for service_name in "${service_names[@]}"
do
  aws --version
  VERSION=$(aws ecs list-task-definitions --family-prefix $service_name --query 'taskDefinitionArns[-1]' --output text | sed "s/.*://")
  sed -i 's@ACCOUNT_ID@'$ACCOUNT_ID'@g' appspec.yaml
  sed -i 's@AWS_REGION@'$AWS_REGION'@g' appspec.yaml
  sed -i 's@orders_version@'$VERSION'@g' appspec.yaml
  echo Completed appspec.yml update on `date`
done
