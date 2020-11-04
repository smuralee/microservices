# Microservices

# APIs
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
