FROM maven:3.5.2-jdk-8-alpine as BUILD

COPY src /usr/src/myapp/src
COPY pom.xml /usr/src/myapp
RUN mvn -f /usr/src/myapp/pom.xml clean package

FROM openjdk:8-alpine
COPY --from=BUILD /usr/src/myapp/target/avlsolver-0.0.1-SNAPSHOT.jar /usr/src/myapp/target/avlsolver.jar
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "target/avlsolver.jar"]