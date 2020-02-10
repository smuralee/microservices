# Microservices

# APIs
* **Product Orders**
  - Endpoint : `/orders`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/orders/info`
    - Returns the `hostname`, `host address` and `public IP address`
  - Endpoint: `/orders/config`
      - Returns the custom configurations in YAML
* **Todos**
  - Endpoint : `/todos`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/todos/info`
    - Returns the `hostname`, `host address` and `public IP address`
  - Endpoint: `/todos/config`
    - Returns the custom configurations in YAML
* **Users**
  - Endpoint : `/users`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/users/info`
    - Returns the `hostname`, `host address` and `public IP address`
  - Endpoint: `/users/todos`
    - Invokes the `/todos` REST API (inter-service communication)
  - Endpoint: `/users/orders`
    - Invokes the `/orders` REST API (inter-service communication)
  - Endpoint: `/users/config`
    - Returns the custom configurations in YAML

# Docker configuration
* [Docker compose](./docker-compose.yml)
