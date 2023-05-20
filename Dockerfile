FROM maven:3.8.5-openjdk-11-slim as build

WORKDIR /workspace/app

RUN mkdir ~/.m2
RUN echo '<settings><mirrors><mirror><id>anupam.com</id><name>anupam</name><url>https://repo.maven.apache.org/maven2/</url><mirrorOf>central</mirrorOf></mirror></mirrors></settings>' >> ~/.m2/settings.xml

COPY pom.xml .
RUN mvn verify clean --fail-never

COPY src src
RUN mvn package -DskipTests

FROM openjdk:11-slim-buster

WORKDIR /app

COPY --from=build /workspace/app/target/cayena-1.0.0.jar app.jar

COPY src/main/resources/application.yml application.yml

ENTRYPOINT java -DskipTests -jar app.jar