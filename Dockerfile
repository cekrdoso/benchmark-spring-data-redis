FROM adoptopenjdk:8-jdk-hotspot

COPY app.jar /app/app.jar

WORKDIR /app

ENTRYPOINT exec java -jar /app/app.jar