FROM openjdk:8-jdk-alpine

ARG JAR_FILE

COPY ${JAR_FILE} /opt/stellar/search/app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/opt/stellar/search/app.jar", "--spring.profiles.active=develop"]