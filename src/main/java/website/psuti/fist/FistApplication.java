package website.psuti.fist;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

@EnableScheduling
@ComponentScan(basePackages = "website.psuti.fist")
@SpringBootApplication
public class FistApplication {
	public static String[] args;
	public static void main(String[] args) {
		FistApplication.args = args;
		/*String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "configuration/spring-config.xml";
		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(appConfigPath));
			appProps.setProperty("spring.datasource.username", "root"); // обновить старое значение
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		SpringApplication.run(FistApplication.class, args);
	}
}
