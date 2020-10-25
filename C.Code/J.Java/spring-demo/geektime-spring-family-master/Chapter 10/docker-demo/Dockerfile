FROM java:8
EXPOSE 8080
ARG JAR_FILE
ADD target/${JAR_FILE} /geektime-10-docker-demo.jar
ENTRYPOINT ["java", "-jar","/geektime-10-docker-demo.jar"]