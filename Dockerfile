FROM gradle:latest AS build

ARG url
ARG user
ARG pass

ENV POSTGRES_URL=$url
ENV POSTGRES_USER=$user
ENV POSTGRES_PASSWORD=$pass

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --info

FROM openjdk:latest

ARG url
ARG user
ARG pass

ENV POSTGRES_URL=$url
ENV POSTGRES_USER=$user
ENV POSTGRES_PASSWORD=$pass

EXPOSE 8080

RUN mkdir "app"

COPY --from=build /home/gradle/src/app/build/libs/*.jar /app/application.jar


ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]

CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]