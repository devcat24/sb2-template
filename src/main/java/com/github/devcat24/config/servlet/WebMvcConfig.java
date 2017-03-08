package com.github.devcat24.config.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
// EnableWebMvc + 'extends WebMvcConfigurerAdapter'
//      => supports Http Converters
//         ex. MappingJackson2HttpMessageConverter,StringHttpMessageConverter, ResourceHttpMessageConverter,
//             Jaxb2RootElementHttpMessagConverter, ByteArrayHttpMessageConverter,
//             AtomFeedHttpMessageConverter, RssChannelHttpMessageConverter
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

}
