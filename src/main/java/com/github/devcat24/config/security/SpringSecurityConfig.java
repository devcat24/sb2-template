package com.github.devcat24.config.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RequestMatcher matcher = new RegexRequestMatcher("/template", "GET");
        http.csrf().requireCsrfProtectionMatcher(matcher);

        http.authorizeRequests()
                // .antMatchers("/", "/emp", "/css/**", "/js/**", "/webjars/**", "/health", "/queue/*", "/error").permitAll()
                // .antMatchers("/env", "/audit/for/*", "/metrics", "/beans", "/info").hasIpAddress("127.0.0.1")
                // .anyRequest().hasRole("ADMIN")
                // .antMatchers("/wel", "/info").hasIpAddress("127.0.0.1");
                .antMatchers("/*").permitAll();
    }
}
