FROM maven:3-openjdk-18

COPY pom.xml /app/
WORKDIR /app
RUN mvn dependency:go-offline
COPY src /app/src
COPY dockerfile /app/
RUN mvn -o clean package -DskipTests

EXPOSE 9081
ENTRYPOINT ["java", "-jar", "/app/target/cup2022-1.0.jar"]