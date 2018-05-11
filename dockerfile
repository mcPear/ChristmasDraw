FROM openjdk:9-jre
COPY ./target/christmasdraw-0.0.1-SNAPSHOT.jar /usr/src/christmasdraw/
COPY test.mv.db /usr/src/christmasdraw/
COPY test.trace.db /usr/src/christmasdraw/
COPY ./src/main/resources/application.properties /usr/src/christmasdraw/
COPY ./src/main/resources/default_application.properties /usr/src/christmasdraw/
WORKDIR /usr/src/christmasdraw
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "christmasdraw-0.0.1-SNAPSHOT.jar", "--spring.config.location=application.properties"]