# Microservices

# APIs
* **Orders**
  - Endpoint : `/`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/info`
    - Returns the `hostname`and `host address`
  - Endpoint: `/config`
      - Returns the custom configurations in YAML
* **Todos**
  - Endpoint : `/`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/info`
    - Returns the `hostname` and `host address`
  - Endpoint: `/config`
    - Returns the custom configurations in YAML
* **Users**
  - Endpoint : `/`
    - Supported operations : **GET**, **POST**, **PUT**, **DELETE**
  - Endpoint: `/info`
    - Returns the `hostname` and `host address`
  - Endpoint: `/todos`
    - Invokes the GET operation on `todos` REST API (inter-service communication)
  - Endpoint: `/orders`
    - Invokes the GET operation on `orders` REST API (inter-service communication)
  - Endpoint: `/config`
    - Returns the custom configurations in YAML

# Docker configuration
* [Docker compose](./docker-compose.yml)
