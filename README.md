# RESTful API Spring Boot

## Technologies used 
* Java 11
* Spring Boot (Spring Web)
* Spring Security (stateless with JWT and roles)
* JPA (hibernate)
* PostgreSQL (with docker)
* OpenAPI 3 [springdoc](https://springdoc.org/)
* ModelMapper (for mapping dtos to jpa entities easily)

## Running the postgres + pgadmin4 container with docker-compose

```bash
# up container
$ docker-compose up -d

# down container
$ docker-compose down
```

## Captures

### Swagger UI

![Swagger UI](https://github.com/jeanp0/rest-api-spring-security-jwt/blob/main/captures/1.png?raw=true)

### Schemas by OpenAPI 3

![Schemas by OpenAPI 3](https://github.com/rest-api-spring-security-jwt/blob/main/captures/2.png?raw=true)

### Success login

![Success login](https://github.com/rest-api-spring-security-jwt/blob/main/captures/3.png?raw=true)

### Fail login

![Fail login](https://github.com/rest-api-spring-security-jwt/blob/main/captures/4.png?raw=true)

### GET with token expired

![GET with token expired](https://github.com/rest-api-spring-security-jwt/blob/main/captures/5.png?raw=true)

### GET without token

![GET without token](https://github.com/rest-api-spring-security-jwt/blob/main/captures/6.png?raw=true)

### GET with valid token

![GET with valid token](https://github.com/rest-api-spring-security-jwt/blob/main/captures/7.png?raw=true)

### Refresh token

![Refresh token](https://github.com/rest-api-spring-security-jwt/blob/main/captures/8.png?raw=true)