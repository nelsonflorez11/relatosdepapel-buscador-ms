# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen final ligera
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/relatosdepapel-buscador-ms-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]