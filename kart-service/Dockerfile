FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} kart-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/kart-service-0.0.1-SNAPSHOT.jar"]