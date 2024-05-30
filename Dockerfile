FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/yetAnotherLibrarySystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]