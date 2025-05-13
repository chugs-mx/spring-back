# Stage 1: Build
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy only what's needed first to leverage Docker cache
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY application.yml ./
RUN gradle build --no-daemon || return 0

# Now copy the rest and build
COPY . .
RUN gradle clean bootJar --no-daemon

# Stage 2: Run
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/application.yml .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
