#FROM amazoncorretto:17-al2-jdk
#WORKDIR /app
#COPY target/API-Tests-Wiremock-Docker-1.0-SNAPSHOT.jar app.jar
#EXPOSE 8089
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests
FROM amazoncorretto:17-al2-jdk
WORKDIR /app
COPY --from=build /app/target/API-Tests-Wiremock-Docker-1.0-SNAPSHOT.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]