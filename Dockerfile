FROM ubuntu:latest AS build

# Update package lists
RUN apt-get update

# Install Maven
RUN apt-get install maven -y

# Copy project files
COPY . .

# Build the application using Maven
RUN mvn package

FROM openjdk:21-jdk-slim

# Expose port 8080
EXPOSE 8080

# Copy the JAR file from the build stage
COPY --from=build target/how-much-pay-0.0.1.jar /app.jar

# Entrypoint to run the application
ENTRYPOINT [ "java", "-jar", "app.jar"]