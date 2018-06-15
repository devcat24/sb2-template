package com.github.devcat24.config.servlet;

import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  // filtering API from exposure
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())  // use '@ApiIgnore' to exclude specific method from exposure
                //.apis(RequestHandlerSelectors.basePackage("com.github.devcat24.mvc.controller")).paths(PathSelectors.ant("/rest/**")) // using package
                //.apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/rest/**")) // allow for all package
                .build();

                //.apiInfo(apiInfo())
                //.globalOperationParameters(global)
                //.select()
                //.apis(RequestHandlerSelectors.any()) // extract every url list of 'RequestMapping'
                //.paths(PathSelectors.ant("/hello/**")).build(); // only filter with '/hello/**' mappings
    }

    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("Dev Template REST sample with Swagger")
                .description("REST API with Swagger")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact(new Contact("DevCat24", "devcat24@gmail.com", "devcat24.github.com"))
                //.contact("devcat24@gmail.com")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("2.0")
                .build();

    }





}
