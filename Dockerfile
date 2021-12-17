FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY target/*.jar /app/demo-market-project.jar

ENTRYPOINT ["java", "-jar", "demo-market-project.jar"]