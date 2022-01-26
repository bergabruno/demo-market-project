FROM openjdk:11

WORKDIR /usr/app

COPY target/*.jar ./app.jar

EXPOSE 8080

ENV PROFILES="docker"
#, URLCLOUD="jdbc:mariadb://y7y2bx.stackhero-network.com:3306/mercado?createDatabaseIfNotExist//=true", USERNAMECLOUD="root", PASSWORDCLOUD="Onap4S1FOaVgNPhUc5UkvjkVH3YSMntT"
#ADICIONAR ENV DO BANCO
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=${PROFILES}"]
#, "--spring.datasource.url=${URLCLOUD}" ,"--spring.datasource.username=${USERNAMECLOUD}", "--spring.datasource.password=${PASSWORDCLOUD}" ]

