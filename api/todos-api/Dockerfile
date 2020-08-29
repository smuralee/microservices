FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-slim
VOLUME /tmp
ADD target/todos-api-1.0.jar app.jar
RUN touch /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
