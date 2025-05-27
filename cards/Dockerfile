# Use the official Java base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

RUN touch test.txt

# Copy the jar file
COPY target/*.jar ./cards.jar

# Expose the port the app runs on
EXPOSE 8002

# Run the application
#ENTRYPOINT ["java", "-jar", "cards.jar"]
ENTRYPOINT ["tail", "-f", "test.txt"]