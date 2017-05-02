package com.github.devcat24.config.servlet;

import com.github.devcat24.config.security.TemplateSecurityFilter;
import com.github.devcat24.servlet.BaseFilter;
import com.github.devcat24.servlet.FooServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServletRegBeanConfig {

    @Bean
    public FilterRegistrationBean regTemplateSecurityFilter(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new TemplateSecurityFilter());
        filterBean.addUrlPatterns("/*");
        filterBean.setName("TemplateSecurityFilter");
        return filterBean;
    }


   @Bean
    public ServletRegistrationBean foo(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new FooServlet(), "/foo");
        // request url
        //     ex. http://localhost:8080/foo
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        servletRegistrationBean.setInitParameters(params);
        servletRegistrationBean.setName("foo");
        return servletRegistrationBean;
    }
    /* // -> this type of url pattern doesn't seem to work in Spring Boot 1.5.x
    @Bean
    public ServletRegistrationBean bar(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new BarServlet(), "/bar*//**//*");
        // request url
        //     ex. http://localhost:8080/bar
        //         http://localhost:8080/bar/1
        //         http://localhost:8080/bar/2
        //            => all invoke same 'BarServlet'
        Map<String, String> params = new HashMap<>();
        params.put("param2", "value2");
        servletRegistrationBean.setInitParameters(params);
        servletRegistrationBean.setName("bar");
        return servletRegistrationBean;
    }*/

    /*@Bean
    public FilterRegistrationBean myFilterRegistrationBean(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new TemplateSecurityFilter());
        filterBean.addUrlPatterns("*//*");
        Map<String, String> params = new HashMap<>();
        params.put("param3", "value3");
        filterBean.setInitParameters(params);
        filterBean.setName("BaseFilter");

        return filterBean;
    }*/

    @Bean
    public FilterRegistrationBean myFilterRegistrationBean02(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new BaseFilter());
        filterBean.addUrlPatterns("/*");
        Map<String, String> params = new HashMap<>();
        params.put("param3", "value3");
        filterBean.setInitParameters(params);
        filterBean.setName("BaseFilter02");

        return filterBean;
    }

    /*@Bean
    public FilterRegistrationBean openEntityManagerInViewFilterBean(){


        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new OpenEntityManagerInViewFilter());
        filterBean.addUrlPatterns("*//*");
//        Map<String, String> params = new HashMap<>();
//        params.put("param3", "value3");
//        filterBean.setInitParameters(params);
        filterBean.setName("OpenEntityManagerInViewFilter");

        System.out.println("-----------filter loaded ----------------------->>>");
        System.out.println("-----------filter loaded ----------------------->>>");
        System.out.println("-----------filter loaded ----------------------->>>");
        System.out.println("-----------filter loaded ----------------------->>>");
        System.out.println("-----------filter loaded ----------------------->>>");
        return filterBean;
    }*/
}
