FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} paymentreceipt-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/paymentreceipt-service-0.0.1-SNAPSHOT.jar"]