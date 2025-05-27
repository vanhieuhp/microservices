# Use the official Java base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar ./loans.jar

# Expose the port the app runs on
EXPOSE 8003

# Run the application
ENTRYPOINT ["java", "-jar", "loans.jar"]