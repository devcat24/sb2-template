package com.github.devcat24.config.servlet;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    // - https://springdoc.org/migrating-from-springfox.html -

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Dev Template REST sample with Open API")
                .description("REST API with Spring Boot")
                .version("v0.0.1")
                //.contact(new Contact().name("Devcat24").email("devcat24@gmail.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .url("http://www.github.com/devcat24"));
    }
    /*
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("springshop-public")
                .pathsToMatch("/public/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder().group("springshop-admin")
                .pathsToMatch("/admin/**")
                .build();
    }
    */
}


//package com.github.devcat24.config.servlet;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
////import org.springframework.context.annotation.Import;
////import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
//
//
////@Import(SpringDataRestConfiguration.class)
//@Configuration
//@EnableSwagger2  // filtering API from exposure
//public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                    .select()
//                    .apis(RequestHandlerSelectors.basePackage("com.github.devcat24.mvc.controller"))
//                    // .apis(RequestHandlerSelectors.any())  // use '@ApiIgnore' to exclude specific method from exposure
//                    .paths(PathSelectors.ant("/api/v1/**"))
//                    // .paths(PathSelectors.regex("/.*"))
//                    // .paths(PathSelectors.any())
//                    .build()
//                    .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo(){
//
//        return new ApiInfoBuilder()
//                .title("Dev Template REST sample with Swagger")
//                .description("REST API with Swagger")
//                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
//                .contact(new Contact("DevCat24", "devcat24@gmail.com", "devcat24.github.com"))
//                //.contact("devcat24@gmail.com")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
//                .version("2.0")
//                .build();
//    }
//}
