# Backend Project

## Requirements

- JDK 11
- Maven 3
- MySQL 8.0.33 Database (You can use the [docker-compose-mysql.yaml](docker-compose-mysql.yaml) as example to run a mysql database local)
- [Postman Collection](Backend-Project.postman_collection.json)

## Running application

To run the application, on IDE, select the class `br.com.weskley.cayena.CayenaApplication.java` and run the `main` method

To run using terminal, use the commando from Spring Boot Maven plugin
```shell
mvn spring-boot:run
```

## Docker

Alternatively, you can build a docker image and run through docker

```shell
docker build . -t backend-project:1.0.0
```
```shell
docker run --rm --network host -p 5500:5500 -it backend-project:1.0.0
```

## Postman Collection