# Build Jar
FROM gradle:7.5.1-jdk18 AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# Deploy
FROM eclipse-temurin:18-jre-alpine
EXPOSE 8080
COPY --from=build /app/build/libs/remitly-notifier-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
