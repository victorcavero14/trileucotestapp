# Start with a base image containing Java runtime and Gradle
FROM gradle:7.2.0-jdk17 as build

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY --chown=gradle:gradle . .

# Build the application
RUN ./gradlew clean build --no-daemon 

# Run the application
FROM openjdk:17-jdk as runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app/app.jar"]