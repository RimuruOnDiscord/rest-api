# Step 1: Build
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Added 'clean' to ensure a fresh start
RUN gradle clean shadowJar --no-daemon -Dorg.gradle.jvmargs="-Xmx1024m"

# Step 2: Run
FROM eclipse-temurin:17-jre
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/manga-api-all.jar /app/manga-api.jar
ENTRYPOINT ["java", "-jar", "/app/manga-api.jar"]
