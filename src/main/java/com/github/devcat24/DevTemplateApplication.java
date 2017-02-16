package com.github.devcat24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@SpringBootApplication
public class DevTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevTemplateApplication.class, args);
	}


}
