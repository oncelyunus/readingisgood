FROM openjdk:11-jre-slim
MAINTAINER "Getir App <getir@app.com>"
WORKDIR /app

COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080