FROM openjdk:8-alpine
VOLUME /tmp
ADD target/orders-api-1.0.jar app.jar
RUN touch /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
