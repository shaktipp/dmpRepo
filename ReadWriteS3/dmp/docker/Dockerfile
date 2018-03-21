FROM openjdk:8-jre-alpine
EXPOSE 8080
VOLUME /tmp
RUN mkdir -p /app-root/runtime/data/DMP-INF
RUN chmod -R 777 /app-root
ENV DMP_DATA_DIR /app-root/runtime/data
ADD DMP-INF/ ${DMP_DATA_DIR}/DMP-INF/
ADD MathApp-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
