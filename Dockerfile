FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY target/demo-market-project-0.0.1-SNAPSHOT.jar /app/demo-market-project.jar

ENTRYPOINT ["java", "-jar", "demo-market-project.jar"]