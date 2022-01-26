FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} readingisgood.jar
ENTRYPOINT ["java","-jar","readingisgood.jar"]