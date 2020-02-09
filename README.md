# Microservices

# APIs
* **Product Orders**
  - Port: `9001`
  - Endpoint : `/product-orders`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/product-orders/info`
    - Returns the `hostname`, `host address` and `public IP address`
* **Todos**
  - Port: `9002`
  - Endpoint : `/todos`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/todos/info`
    - Returns the `hostname`, `host address` and `public IP address`
* **Users**
  - Port: `9003`
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
