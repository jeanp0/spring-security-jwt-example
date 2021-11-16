# RESTful API Spring Boot

## Technologies used 
* Java 11
* Spring Boot (Spring Web)
* Spring Security (stateless with JWT)
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

### Swagger UI of API RESTful

![Swagger UI of RESTful API](https://github.com/jeanp0/rest-api-nestjs-mongo/blob/main/captures/1.png?raw=true)

### Schemas of API RESTful

![Schemas of API RESTful](https://github.com/jeanp0/rest-api-nestjs-mongo/blob/main/captures/2.png?raw=true)

### Test login endpoint

![Test login endpoint](https://github.com/jeanp0/rest-api-nestjs-mongo/blob/main/captures/3.png?raw=true)

### Test get user by id endpoint without token

![Test get user by id endpoint without token](https://github.com/jeanp0/rest-api-nestjs-mongo/blob/main/captures/5.png?raw=true)

### Test get user by id endpoint with token

![Test get user by id endpoint with token](https://github.com/jeanp0/rest-api-nestjs-mongo/blob/main/captures/4.png?raw=true)