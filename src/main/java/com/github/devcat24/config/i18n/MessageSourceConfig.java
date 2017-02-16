package com.github.devcat24.config.i18n;

import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageSourceConfig extends WebMvcConfigurerAdapter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MessageSourceConfig.class);

    @Bean
    public MessageSource messageSource() {
        logger.debug("MessageSourceConfig.messageSource: Generate MessageSource Bean");

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        // find propertie files under 'messages' folder with 'messages_**_**.properties'
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver(){
        logger.debug("MessageSourceConfig.localeResolver: Generate LocalResolver Bean");

        // type #1 - using cookie for locale
        //         - set locale in accordance with Browser Cookie
        // CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        // localeResolver.setDefaultLocale(new Locale("en"));
        // localeResolver.setCookieName("myLocalCookie");
        // localeResolver.setCookieMaxAge(4800);

        // type #2 - set locale in accordance with session
        SessionLocaleResolver localeResolver=new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en_US"));
        // set default local using declaration

        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        // if client browser requests with 'lang' parameter, accepts it as local

        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.debug("MessageSourceConfig.addInterceptors: Register MessageSource LocaleChangeInterceptor");

        registry.addInterceptor(localeChangeInterceptor());
        // add interceptor to 'InterceptorRegistry'
    }
}
