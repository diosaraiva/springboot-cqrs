FROM openjdk:19-alpine as builder
ARG JAR_FILE=target/nxtlvl-query-0.0.1.jar
COPY ${JAR_FILE} nxtlvl-query-0.0.1.jar
RUN java -Djarmode=layertools -jar nxtlvl-query-0.0.1.jar extract

FROM openjdk:19-alpine
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
