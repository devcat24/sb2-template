package com.github.devcat24.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

import java.util.concurrent.TimeUnit;

//@EnableWebMvc ==> makes problem with 'addResourceHandlers & /static/**' configuration ! -> just use '@Configuration'
@Configuration
//    @Configuration + 'extends WebMvcConfigurerAdapter'
//      => supports Http Converters
//         ex. MappingJackson2HttpMessageConverter,StringHttpMessageConverter, ResourceHttpMessageConverter,
//             Jaxb2RootElementHttpMessagConverter, ByteArrayHttpMessageConverter,
//             AtomFeedHttpMessageConverter, RssChannelHttpMessageConverter
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**", "/webjars/**")
                .addResourceLocations("/resources/", "/webjars/", "classpath:/META-INF/resources/webjars/")
                .setCacheControl(CacheControl.noCache().maxAge(30L, TimeUnit.DAYS).cachePublic())
                .resourceChain(true)
                .addResolver(new WebJarsResourceResolver());




        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

/*        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");*/

    }





}
