FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN ./mvnw dependency:resolve
CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8080