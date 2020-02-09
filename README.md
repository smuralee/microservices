# Microservices

# APIs
* **Product Orders**
  - Endpoint : `/product-orders`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/product-orders/info`
    - Returns the `hostname`, `host address` and `public IP address`
* **Todos**
  - Endpoint : `/todos`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/todos/info`
    - Returns the `hostname`, `host address` and `public IP address`
* **Users**
  - Endpoint : `/users`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/users/info`
    - Returns the `hostname`, `host address` and `public IP address`
  - Endpoint: `/users/todos`
    - Invokes the `/todos` REST API (inter-service communication)
  - Endpoint: `/users/product-orders`
    - Invokes the `/product-orders` REST API (inter-service communication)

# Docker configuration
* [Docker compose](./docker-compose.yml)
