FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/gatewayserver-1.0.0-SNAPSHOT.jar ./gatewayserver.jar

EXPOSE 8072

ENTRYPOINT ["java", "-jar", "gatewayserver.jar"]