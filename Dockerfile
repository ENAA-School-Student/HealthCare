FROM eclipse-temurin:21
COPY target/HealthCare-0.0.1-SNAPSHOT.jar HealthCare-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/HealthCare-0.0.1-SNAPSHOT.jar"]