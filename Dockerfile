# Stage 1: Build Stage
FROM gradle:7.6-jdk17 AS build
WORKDIR /app

# Copy Gradle wrapper and dependencies first for layer caching
COPY gradle gradle
COPY gradlew ./
COPY build.gradle ./
COPY settings.gradle ./
RUN ./gradlew dependencies --no-daemon

# Copy the source code and build the application
COPY src ./src
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Runtime Stage
FROM openjdk:17-alpine
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Set environment variables
ENV LOG_FILE_PATH=/app/logs/lms.log \
    JAVA_OPTS=""

# Create logs directory
RUN mkdir -p /app/logs

# Expose the application port
EXPOSE 8080

# Run the application with logging and additional Java options
CMD ["sh", "-c", "java $JAVA_OPTS -Dlogging.file.path=$LOG_FILE_PATH -jar app.jar"]
