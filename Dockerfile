## Build JAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

## Runtime Container
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

## E2E Test Runner
FROM maven:3.9.6-eclipse-temurin-17 AS e2e-tests
WORKDIR /app
COPY . /app

## Install Google Chrome
RUN apt-get update && \
    apt-get install -y wget unzip curl gnupg2 && \
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb && \
    rm ./google-chrome-stable_current_amd64.deb

## Install compatible ChromeDriver (latest available version)
RUN CHROMEDRIVER_VERSION=$(curl -sS https://chromedriver.storage.googleapis.com/LATEST_RELEASE) && \
    wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip && \
    unzip /tmp/chromedriver.zip -d /usr/local/bin && \
    chmod +x /usr/local/bin/chromedriver && \
    rm /tmp/chromedriver.zip

## Let Chrome run as root inside container
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER=/usr/local/bin/chromedriver

## Run E2E tests using Failsafe plugin
CMD ["mvn", "verify", "-P", "e2e-tests"]
