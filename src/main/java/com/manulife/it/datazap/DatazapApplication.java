package com.manulife.it.datazap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DatazapApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatazapApplication.class, args);
	}

}
