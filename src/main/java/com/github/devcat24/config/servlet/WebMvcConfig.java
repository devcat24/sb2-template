package com.github.devcat24.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

import java.util.concurrent.TimeUnit;
// import org.springframework.http.CacheControl;
// import org.springframework.web.servlet.resource.WebJarsResourceResolver;
// import java.util.concurrent.TimeUnit;


// WebMvcConfigurerAdapter -> Deprecated -> WebMvcConfigurer
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // Add exception to 'SOP(Single-Origin Policy)'
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // The uri '/allow-to-9000' can be invoked from "http://localhost:9000" as the exception of SOP(Single-Origin Policy)
        registry.addMapping("/allow-to-9000").allowedOrigins("http://localhost:9000");
        // Any requests from "http://localhost:9000" can be invoked as the exception of SOP(Single-Origin Policy)
        registry.addMapping("/**").allowedOrigins("http://localhost:18080");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");


        /* // No more required on Spring Boot2
        registry.addResourceHandler("/static/**", "/webjars/**")
                .addResourceLocations("/resources/", "/webjars/", "classpath:/META-INF/resources/webjars/")
                .setCacheControl(CacheControl.noCache().maxAge(30L, TimeUnit.DAYS).cachePublic())
                .resourceChain(true)
                .addResolver(new WebJarsResourceResolver());
        */

    }
}
