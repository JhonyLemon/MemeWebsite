FROM maven:3.8.6-openjdk-11 AS build
COPY src ./src
COPY pom.xml ./
RUN mvn clean package install -DskipTests=true
CMD ["mvn", "spring-boot:run"]