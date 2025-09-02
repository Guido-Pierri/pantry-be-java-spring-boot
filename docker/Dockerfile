FROM eclipse-temurin:21-jre-alpine
VOLUME /app
COPY target/Pantry-BE-0.0.1-SNAPSHOT.jar /app/
RUN adduser -D user
USER user
ENTRYPOINT ["java"]
CMD ["-XX:+EnableDynamicAgentLoading", "-jar", "/app/Pantry-BE-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080