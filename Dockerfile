FROM gradle:7-jdk11 as builder

USER root

ENV APP_DIR /app
WORKDIR $APP_DIR

COPY build.gradle.kts $APP_DIR/
COPY settings.gradle.kts $APP_DIR/

RUN gradle dependencies

COPY . $APP_DIR

RUN gradle build -x test

USER guest
## -----------------------------------------------------------------------------

FROM openjdk:11-slim-buster

WORKDIR /app

COPY --from=builder /app/init.sh /app
COPY --from=builder /app/build/libs/game-find-three-1.0.jar /app/

EXPOSE 8080

ENTRYPOINT ["sh", "init.sh"]