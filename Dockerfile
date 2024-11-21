FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
VOLUME ~/.m2:/root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar library-api.jar
ENTRYPOINT ["java", "-jar","library-api.jar"]