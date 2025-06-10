# Use the official Java base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the jar file
COPY build/libs/loans-1.0.0-SNAPSHOT.jar ./loans.jar

# Expose the port the app runs on
EXPOSE 8003

ENTRYPOINT ["java", "-jar", "loans.jar"]