FROM openjdk:8
ADD target/fist.jar fist.jar
ADD target/classes/downloadArchive *
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fist.jar"]