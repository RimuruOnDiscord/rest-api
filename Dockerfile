# Step 1: Build the application
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

# Step 2: Run the application
# We use eclipse-temurin because openjdk is deprecated
FROM eclipse-temurin:17-jre
EXPOSE 8080
RUN mkdir /app
# This copies the generated jar from the build stage
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/manga-api.jar
ENTRYPOINT ["java", "-jar", "/app/manga-api.jar"]
