FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-slim
RUN useradd -u 101 alpha
RUN mkdir -p /home/alpha/app && chown -R alpha:alpha /home/alpha/app
WORKDIR /home/alpha/app
USER alpha
ADD --chown=alpha:alpha target/orders-api-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./app.jar"]
