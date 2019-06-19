FROM openjdk:8
ADD target/fist.jar fist.jar
VOLUME /fist
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fist.jar"]