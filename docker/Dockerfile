FROM eclipse-temurin:21-jre-alpine
VOLUME /app
COPY app.jar /app/
RUN adduser -D user
USER user
ENTRYPOINT ["java"]
CMD ["-XX:+EnableDynamicAgentLoading", "-jar", "/app/app.jar"]
EXPOSE 8080