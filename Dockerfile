FROM adoptopenjdk:11-jdk-hotspot

ARG JAR_FILE=build/libs/eventcafecloud-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=ebprod","/app.jar"]