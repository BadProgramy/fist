package website.psuti.fist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "website.psuti.fist")
@SpringBootApplication
public class FistApplication {

	public static void main(String[] args) {
		SpringApplication.run(FistApplication.class, args);
	}
}
