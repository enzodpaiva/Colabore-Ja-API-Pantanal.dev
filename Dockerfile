# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim-buster

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local build target directory to the container
COPY target/colabore-ja-0.0.1-SNAPSHOT.jar /app/colabore-ja-0.0.1-SNAPSHOT.jar

# Expose the port that your Spring Boot application will listen on (adjust as needed)
EXPOSE 8080

# Start the Spring Boot application when the container starts
CMD ["java", "-jar", "colabore-ja-0.0.1-SNAPSHOT.jar"]
