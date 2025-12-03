# Etapa 1: Construção (Usando Maven com Temurin)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Execução (Usando Temurin Linux leve)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Copia o JAR gerado. O * ajuda a pegar o nome independente da versão
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]