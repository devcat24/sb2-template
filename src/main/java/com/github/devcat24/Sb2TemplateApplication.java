package com.github.devcat24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })	// -> disable spring boot security auto configuration
public class Sb2TemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sb2TemplateApplication.class, args);
	}

}
