FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} specialday-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/specialday-service-0.0.1-SNAPSHOT.jar"]