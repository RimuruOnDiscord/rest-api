# Step 1: Build
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Use --no-daemon and -Xmx to manage memory
RUN gradle shadowJar --no-daemon -Dorg.gradle.jvmargs="-Xmx1024m"

# Step 2: Run
FROM eclipse-temurin:17-jre
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/manga-api-all.jar /app/manga-api.jar
# Clean up some space if needed
ENTRYPOINT ["java", "-jar", "/app/manga-api.jar"]
