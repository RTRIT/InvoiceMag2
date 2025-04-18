#Dockerfile is a recipe for creating image

# Stage 1: build app
#Start with a Maven image that includes jdk-21
FROM maven:3-amazoncorretto-21-al2023 AS build

#Copy source code and pom.xml file tp /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

#Build source code with maven
RUN mvn package -DskipTests

#Stage 2: create image
#Start with Amazon Coretto JDK 21
FROM amazoncorretto:21.0.4-al2023

#Set working folder to app and copy compiled file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

#Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]