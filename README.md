# RESTful API Spring Boot

## Technologies used 
* Java 11
* Spring Boot (Spring Web)
* Spring Security (stateless with JWT and roles)
* JPA (hibernate)
* Javax Validations to validate the properties of the entities in the requests.
* PostgreSQL 14 + pgAdmin (with docker)
* OpenAPI 3 [springdoc](https://springdoc.org/)
* ModelMapper (for mapping dtos to jpa entities easily)

## Run PostgreSQL + pgAdmin with Docker

```bash
# up containers
$ docker-compose up -d

# down containers
$ docker-compose down
```

## Microservice (Dockerization)

```bash
$ docker build -t jeanpierm/rest-api-spring-boot .
```

Run a **postgres**, **pgadmin** and **spring-app** images with docker-compose. The **spring-app** image is built automatically if it doesn't exist.

```bash
# up containers
$ docker-compose -f production.yml up -d

# down containers
$ docker-compose -f production.yml down

# view logs
$ docker-compose -f production.yml logs -f
```


## Captures

### Application running with Docker via docker-compose

![Application running with Docker](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/9.png?raw=true)

### Swagger UI

![Swagger UI](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/1.png?raw=true)

### Schemas by OpenAPI 3

![Schemas by OpenAPI 3](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/2.png?raw=true)

### Success login

![Success login](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/3.png?raw=true)

### Fail login

![Fail login](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/4.png?raw=true)

### GET with token expired

![GET with token expired](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/5.png?raw=true)

### GET without token

![GET without token](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/6.png?raw=true)

### GET with valid token

![GET with valid token](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/7.png?raw=true)

### Refresh token

![Refresh token](https://github.com/jeanpierm/rest-api-spring-boot/blob/main/captures/8.png?raw=true)