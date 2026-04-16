# Etapa 1: Construcción (Build)
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiamos el wrapper de Maven y el archivo de configuración (pom.xml)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Descargamos las dependencias para que queden en caché y el build sea más rápido después
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen de ejecución (Runtime)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Añadimos un usuario sin privilegios por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos solo el archivo JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8081

# Ejecución optimizada para contenedores
ENTRYPOINT ["java", "-jar", "app.jar"]