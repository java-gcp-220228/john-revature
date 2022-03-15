FROM openjdk:latest

EXPOSE 7070

RUN mkdir "app"

COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar


ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]