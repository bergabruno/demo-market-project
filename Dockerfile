FROM openjdk:11

WORKDIR /usr/app

COPY target/*.jar ./app.jar

EXPOSE 8080

ENV PROFILES="docker"
#ADICIONAR ENV DO BANCO
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=${PROFILES}"]

