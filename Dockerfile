FROM eclipse-temurin:11-jdk
MAINTAINER jeanpiermendoza@outlook.com
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demo-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/demo-1.0.0.jar"]
