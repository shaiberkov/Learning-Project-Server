FROM maven:3.8.4-openjdk-17 as build
WORKDIR /schoolManagement
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests




FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY --from=build /schoolManagement/target/learningProjectServer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

