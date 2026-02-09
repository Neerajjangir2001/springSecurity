# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/securityPractice-0.0.1-SNAPSHOT.jar . 
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "securityPractice-0.0.1-SNAPSHOT.jar"]
