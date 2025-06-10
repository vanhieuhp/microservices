# Use the official Java base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the jar file
COPY build/libs/eurekaserver-0.0.1-SNAPSHOT.jar ./eurekaserver.jar

# Expose the port the app runs on
EXPOSE 8010

# Run the application
ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]