# Use the official Java base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the jar file
COPY build/libs/configserver-0.0.1-SNAPSHOT.jar ./configserver.jar

# Expose the port the app runs on
EXPOSE 8071

# Run the application
ENTRYPOINT ["java", "-jar", "configserver.jar"]