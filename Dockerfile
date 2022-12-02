FROM gradle:jdk as gradleimage
COPY covidApp /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build -x test

FROM eclipse-temurin:11.0.12_7-jdk
COPY --from=gradleimage /home/gradle/source/build/libs/covidApp-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT ["java", "-jar", "covidApp-0.0.1-SNAPSHOT.jar"]