# reading is good
Spring Boot Application using Spring boot, mongodb, docker also for cache redis

## Getting Started
Below instruction will help you to run spring boot application

### Prerequisites
* You have to provide proper dependency for spring boot application, its already defined in mvn root pom.
### Installing

* step 1: maven installing at root
```
"mvn clean install -DskipTests"
```
* step 2: spring app build via docker
```
"docker build -t readingisgood ."
```
* step 3: start app also required tools via docker compose
```
"docker-compose up -d"
```
* step 4: to list all executed process via following command
```
    "docker ps"
```

* step 5: if you want to connect to  redis via cli 
```
"docker exec -it redis sh"
``` 
and also password is **mypassword**

* step 6 if you need the client to connect mongodb, visit the page https://studio3t.com/download/



## Authors
*  [Yunus Öncel](https://github.com/oncelyunus)
