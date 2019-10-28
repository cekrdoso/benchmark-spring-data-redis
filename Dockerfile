FROM openjdk:11-jdk-oracle

COPY app.jar /app

WORKDIR /app

ENTRYPOINT exec java -jar /app/app.jar