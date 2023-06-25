# Prueba técnica Trileuco

##  Víctor Manuel Cavero Gracia

## ¿Cómo usar el proyecto?

## Más fácil con Dockercompose

`docker compose up`

###  Backend

- [Swagger URL](http://localhost:8080/swagger-ui/index.html)
- [Luke search](http://localhost:8080/swapi-proxy/person-info?name=Luke)

####  Docker Backend

- `sudo docker build -t back-trileucotestapp .`
- `sudo docker run -p 8080:8080 back-trileucotestapp`

####   Sin Docker Backend

- `./gradlew build`
- `./gradlew bootRun`

### Frontend

- [Frontend](http://localhost:3000/)

####  Docker Frontend

- `sudo docker build -t front-trileucotestapp .`
- `docker run -p 3000:3000 front-trileucotestapp`

#### Sin Docker Frontend

- `npm install`
- `npm start`
