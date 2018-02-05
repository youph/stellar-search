FROM openjdk:8-jdk-alpine

# Install deps for health check
RUN apk add --no-cache curl jq

HEALTHCHECK --start-period=30s CMD test "$(curl -fs 'http://localhost:8080/health' | jq -r .status)" = UP

ARG JAR_FILE

COPY ${JAR_FILE} /opt/stellar/search/app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/opt/stellar/search/app.jar"]

EXPOSE 8080
