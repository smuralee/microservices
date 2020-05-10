# Microservices

# APIs
* **Orders**
  - Endpoint : `/orders`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/info`
    - Returns the `hostname`and `host address`
  - Endpoint: `/config`
      - Returns the custom configurations in YAML
* **Todos**
  - Endpoint : `/todos`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/info`
    - Returns the `hostname` and `host address`
  - Endpoint: `/config`
    - Returns the custom configurations in YAML
* **Users**
  - Endpoint : `/users`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/info`
    - Returns the `hostname` and `host address`
  - Endpoint: `/users/todos`
    - Invokes the GET operation on `todos` REST API (inter-service communication)
  - Endpoint: `/users/orders`
    - Invokes the GET operation on `orders` REST API (inter-service communication)
  - Endpoint: `/config`
    - Returns the custom configurations in YAML

# Docker configuration
* [Docker compose](./docker-compose.yml)
