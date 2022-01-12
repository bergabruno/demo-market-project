FROM openjdk:11
#Escolher a imagem do JDK

RUN mkdir -p /app/demo-market

COPY target/demo-market-project-0.0.1-SNAPSHOT.jar /app/demo-market-project/
#copia o .jar da aplicacao para a pasta /app do docker
#Error: Unable to access jarfile demo-market-project.jar

EXPOSE 8080

WORKDIR /app
#diretorio principal - dps que finalizar o build, gera esse diretorio e trabalha dentro dele

ENTRYPOINT ["java", "-jar", "demo-market-project.jar"]
#como se fosse um ls

# RUN chown -R /users/brunobergamasco/IdeaProject/