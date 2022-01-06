FROM openjdk
#Escolher a imagem do JDK

WORKDIR /app
#diretorio principal

COPY target/demo-market-project-0.0.1-*.jar /app/demo-market-project.jar
#copia o .jar da nossa aplicacao para a pasta /app

ENTRYPOINT ["java", "-jar", "demo-market-project.jar"]

# RUN chown -R /users/brunobergamasco/IdeaProject/