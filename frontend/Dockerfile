# Etapa 1: Build con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime con Java 21 JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# El frontend suele usar el 8080 por defecto
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]