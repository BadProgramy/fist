FROM openjdk:8
ADD target/fist.jar fist.jar
VOLUME /fist
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fist.jar","--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver","--spring.datasource.url=jdbc:mysql://mysql-fist:3306/fist?useLegacyDatetimeCode=false&useUnicode=true&characterEncoding=UTF8&amp&serverTimezone=UTC", "--spring.datasource.username=root", "--spring.datasource.password=root"]