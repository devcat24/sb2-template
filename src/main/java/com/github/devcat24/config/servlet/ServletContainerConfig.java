package com.github.devcat24.config.servlet;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.Primary;
//import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
//import freemarker.cache.ClassTemplateLoader;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
//import java.io.IOException;

@Configuration
@ServletComponentScan({"com.github.devcat24.servlet"})
// '@ServletComponentScan' only works for embedded servlet containers !
public class ServletContainerConfig extends SpringBootServletInitializer {
    @Value("${tomcat.ajp.port}")
    int ajpPort;

    @Value("${tomcat.ajp.remoteauthentication}")
    String remoteAuthentication;

    @Value("${tomcat.ajp.enabled}")
    boolean tomcatAjpEnabled;

//    @Bean
//    public InternalResourceViewResolver setupJSTLViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//
//        resolver.setViewClass(JstlView.class);
//        resolver.setPrefix("/WEB-INF/view/");
//        resolver.setSuffix(".jsp");
//        resolver.setContentType("text/html");
//        resolver.setOrder(0);
//
//        return resolver;
//    }

    // https://o7planning.org/en/11257/using-multiple-viewresolvers-in-spring-boot
//    @Bean(name = "viewResolver")
//    public ViewResolver getViewResolver() {
//        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//
//        viewResolver.setCache(true);
//        viewResolver.setPrefix("/freemarker/");
//        viewResolver.setSuffix(".ftl");
//        viewResolver.setOrder(1);
//        return viewResolver;
//    }

//    @Bean(name = "freemarkerConfig")
//    public FreeMarkerConfigurer getFreemarkerConfig() {
//        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
//
//        config.setTemplateLoaderPath("classpath:/templates/freemarker");
//        return config;
//    }


    // mod_jk_ajp secret issue with Spring Boot +2.2.5
    //   > https://stackoverflow.com/questions/60501470/springboot-the-ajp-connector-is-configured-with-secretrequired-true-but-the-s
    // 
    //  Apache 'secret' support for mod_proxy_ajp : +2.4.42
    //   > https://serverfault.com/questions/1004541/setting-up-ajp-secret-between-apache-and-tomcat
    //   > https://httpd.apache.org/docs/2.4/mod/mod_proxy_ajp.html
    //   > https://httpd.apache.org/docs/trunk/mod/mod_proxy_ajp.html
    //  Apache latest +2.4.42 installation
    //   > http://httpd.apache.org/download.cgi#apache24
    //  Configure Apache load balancer with mod proxy ajp
    //   > https://wiki.logicaldoc.com/wiki/Configure_Apache_load_balancer_with_mod_proxy_ajp

    @Bean
    public TomcatServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        if (tomcatAjpEnabled)
        {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("http");
            //noinspection rawtypes
            final AbstractAjpProtocol protocol = (AbstractAjpProtocol) ajpConnector.getProtocolHandler();
            ajpConnector.setSecure(true);
            protocol.setSecret(remoteAuthentication);

            // https://www.tenable.com/blog/cve-2020-1938-ghostcat-apache-tomcat-ajp-file-readinclusion-vulnerability-cnvd-2020-10487 - CVE-2020-1938: Ghostcat (AJP)
            // https://access.redhat.com/solutions/4851251 - CVE-2020-1938: Ghostcat (AJP)
            // https://tomcat.apache.org/tomcat-9.0-doc/config/ajp.html
            // https://nirsa.tistory.com/158

            // Disabling secret - not recommended !
            // ajpConnector.setSecure(false);
            // protocol.setSecretRequired(false);
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;
    }



    /* --> for spring boot 1.x
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        if (tomcatAjpEnabled)
        {
            Connector ajpConnector = new Connector("AJP/1.3");
            // ajpConnector.setProtocol("AJP/1.3");  // Deprecated - Protocol should be defined with constructor
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("http");
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;
    }
    */
   /* @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> {
            if (server instanceof TomcatServletWebServerFactory) {
                ((TomcatServletWebServerFactory)   server).addAdditionalTomcatConnectors(redirectConnector());
            }
        };
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("AJP/1.3");
        connector.setScheme("http");
        connector.setPort(ajpPort);
        connector.setSecure(false);
        connector.setAllowTrace(false);
        return connector;
    }*/

}
