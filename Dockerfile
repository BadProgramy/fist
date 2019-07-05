FROM openjdk:8
ADD target/fist.jar fist.jar
VOLUME /fist
EXPOSE 8080
ENV LANGUAGE ru_RU.UTF-8
ENV LANG ru_RU.UTF-8
ENV LC_ALL ru_RU.UTF-8
ENTRYPOINT ["java", "-jar", "fist.jar","--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver","--spring.datasource.url=jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/heroku_53caec219344d97?useLegacyDatetimeCode=false&useUnicode=true&characterEncoding=UTF8&amp&serverTimezone=UTC", "--spring.datasource.username=bba802876388bd", "--spring.datasource.password=0f9211f1"]