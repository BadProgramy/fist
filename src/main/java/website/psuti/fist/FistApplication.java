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
import website.psuti.fist.constant.PathConstant;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

@EnableScheduling
@ComponentScan(basePackages = "website.psuti.fist")
@SpringBootApplication
public class FistApplication {
	public static String[] args;
	public static void main(String[] args) {
		FistApplication.args = args;

		java.io.File ff = new java.io.File(PathConstant.CONFIG_BD.getPath());
		if (ff.exists()) {
			if (args.length < 1) args = new String[4];
			FileReader fr = null;
			try {
				fr = new FileReader(ff);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(fr);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("application.properties");
			Properties properties = new Properties();
			try {
				properties.load(input);

				String line = reader.readLine();
				while (line != null) {
					switch (line.split("->")[0]) {
					/*case "db_user" : properties.setProperty("spring.datasource.username", line.split("=")[1]); break;
					case "db_pass" : properties.setProperty("spring.datasource.password", line.split("=")[1]); break;
					case "db_url" : properties.setProperty("spring.datasource.url", line.split("=")[1]); break;
					case "db_driver_name" : properties.setProperty("spring.datasource.driver-class-name", line.split("=")[1]); break;*/
						case "db_user":
							args[0] = "--spring.datasource.username=" + line.split("->")[1];
							break;
						case "db_pass":
							args[1] = "--spring.datasource.password=" + line.split("->")[1];
							break;
						case "db_url":
							args[2] = "--spring.datasource.url=" + line.split("->")[1];
							break;
						case "db_driver_name":
							args[3] = "--spring.datasource.driver-class-name=" + line.split("->")[1];
							break;
					}
					// считываем остальные строки в цикле
					line = reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			SpringApplication springApplication = new SpringApplication();
			springApplication.setDefaultProperties(properties);
			FistApplication.args = args;
			springApplication.run(FistApplication.class, args);
		}
		else SpringApplication.run(FistApplication.class, args);
	}
}
