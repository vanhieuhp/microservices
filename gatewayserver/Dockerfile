FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/gatewayserver-0.0.1-SNAPSHOT.jar ./gatewayserver.jar
COPY ./opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

EXPOSE 8072

ENTRYPOINT ["java", "-jar", "gatewayserver.jar"]