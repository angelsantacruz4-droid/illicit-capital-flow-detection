# Use OpenJDK 17 as the base image for the build stage
FROM openjdk:17-jdk AS build

# Set the working directory to /app
WORKDIR /app

# Copy the local pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B 

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use OpenJDK 17 as the base image for the production stage
FROM openjdk:17-jre-slim AS runtime

# Set the working directory to /app
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set the command to run the application
ENTRYPOINT ["java","-jar","app.jar"]