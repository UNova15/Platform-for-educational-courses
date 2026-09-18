FROM maven:3.9.14-eclipse-temurin-25 AS builder

WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/application/target/application.jar app.jar

CMD ["java", "-jar", "app.jar"]