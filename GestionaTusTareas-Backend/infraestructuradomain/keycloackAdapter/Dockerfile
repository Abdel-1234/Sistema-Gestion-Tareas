
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

FROM eclipse-temurin:17-alpine

RUN addgroup -g 1028 devopsc \
    && adduser -D -G devopsc admin

VOLUME /temp

ARG JAR_FILE

COPY $JAR_FILE /temp/app.jar

RUN chown -R admin:devopsc /temp

USER admin

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /temp/app.jar"]
