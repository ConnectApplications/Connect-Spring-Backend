FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/connect-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
EXPOSE 5432
ENTRYPOINT ["java", "-jar", "app.jar"]