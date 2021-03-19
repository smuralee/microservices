#!/usr/bin/env bash

aws --version
ORDERS_TASK_DEFINITION=$(aws ecs list-task-definitions --family-prefix orders --query 'taskDefinitionArns[-1]' --output text)
USERS_TASK_DEFINITION=$(aws ecs list-task-definitions --family-prefix users --query 'taskDefinitionArns[-1]' --output text)
cat <<EOF >>appspec.yaml
version: 0.0
Resources:
  - TargetService:
      Type: AWS::ECS::Service
      Properties:
        TaskDefinition: $ORDERS_TASK_DEFINITION
        LoadBalancerInfo:
          ContainerName: "orders"
          ContainerPort: 8080
  - TargetService:
      Type: AWS::ECS::Service
      Properties:
        TaskDefinition: $USERS_TASK_DEFINITION
        LoadBalancerInfo:
          ContainerName: "users"
          ContainerPort: 8080
EOF
echo Completed appspec.yml update on `date`
