# Use the official Java base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar ./loans.jar
COPY ./opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

# Expose the port the app runs on
EXPOSE 8003

# JAVA_TOOLS_OPTIONS: "-javaagent:/app/opentelemetry-javaagent.jar"
#       OTEL_EXPORTER_OTLP_ENDPOINT: http://tempo:4317
#       OTEL_METRICS_EXPORTER: none
#       OTEL_LOGS_EXPORTER: none
# Run the application


ENTRYPOINT ["java", "-jar", "loans.jar", "-javaagent:/app/opentelemetry-javaagent.jar", "-Dotel.exporter.otlp.endpoint=http://tempo:4317", "-Dotel.metrics.exporter=none", "-Dotel.logs.exporter=none"]