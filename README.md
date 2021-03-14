# Microservices

## Pre-requisites
* Create the PostGreSQL instances in AWS
* Connect to default database - `postgres` *URL:* **jdbc:postgresql://host.us-east-1.rds.amazonaws.com:5432/postgres**
* Create the databases `orders` and `users`
  ```postgresql
  create database orders;
  create database users;
  ```
* DDL will be executed by application during initialization
* Create the secrets for storing the RDS database credentials
  * `dev/rds/api/orders`
  * `dev/rds/api/users`

## APIs
* **Orders**
  - Endpoint : `/orders`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/config`
    - Supported operations: **GET**
    - Returns the custom configurations in YAML
* **Users**
  - Endpoint : `/users`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/config`
    - Supported operations: **GET**
    - Returns the custom configurations in YAML
  - Endpoint: `/users/{id}/orders`
    - Supported operations: **GET**
    - Returns the user data with the orders information for the `{id}`

# Docker configuration
* [Docker compose](./docker-compose.yml)
