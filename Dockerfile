# Use the official OpenJDK 17 image as the base image
#FROM openjdk:17-jdk-slim-buster

#FROM maven:3.8.7-openjdk-17-slim

FROM maven:3.8.1-openjdk-17-slim

#FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# RUN #apt-get update && apt-get install -y maven

# # Copy the JAR file from your local build target directory to the container
# COPY target/colabore-ja-0.0.1-SNAPSHOT.jar /app/colabore-ja-0.0.1-SNAPSHOT.jar

# # Expose the port that your Spring Boot application will listen on (adjust as needed)
# EXPOSE 8080

# # Start the Spring Boot application when the container starts
# CMD ["java", "-jar", "colabore-ja-0.0.1-SNAPSHOT.jar"]

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY docker-entrypoint.sh ./
COPY src ./src

#COPY target/*.jar /app/colabore-ja-0.0.1-SNAPSHOT.jar
#CMD java -XX:+UseContainerSupport -jar app.jar