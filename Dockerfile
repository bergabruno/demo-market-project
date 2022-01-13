FROM openjdk:11
#Escolher a imagem do JDK

WORKDIR /app

COPY target/demo-market-project-0.0.1-SNAPSHOT.jar app/
#copia o .jar da aplicacao para a pasta /app do docker
#Error: Unable to access jarfile demo-market-project.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo-market-project.jar"]
#como se fosse um ls
