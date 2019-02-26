package com.github.devcat24.config.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // ### Spring Security Basic Configuration
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf should be disabled to handle 'POST' request
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();

        /*
        http
                .authorizeRequests()
                .antMatchers("/api/v1/emp").permitAll()
                //.antMatchers(HttpMethod.POST, "/restemp/*").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic();
         */
    }

    // ### Spring Security Google OAuth2 Configuration
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //             // .authorizeRequests()
    //             // .anyRequest().authenticated()
    //             // .and()
    //             // .oauth2Login();
    //
    //             .authorizeRequests()
    //             .antMatchers("/oauth_login")
    //             .permitAll()
    //             .anyRequest()
    //             .authenticated()
    //             .and()
    //             .oauth2Login()
    //             //.failureUrl("/login_failure")
    //             //.defaultSuccessUrl("/login_success")
    //             .loginPage("/oauth_login");
    //
    // }



    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder authentication)         throws Exception {
    //     authentication.inMemoryAuthentication()
    //             .withUser("admin")
    //             .password(passwordEncoder().encode("password"))
    //             .authorities("ROLE_USER");
    // }
    //
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    /*   // -> LDAP integration
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns("ou=User,ou=HR,ou=system")
                .groupSearchBase("ou=Group")
                .userSearchFilter("uid={0}")
                .contextSource()
                .url("ldap://myldapserver.com:389/ou=HR,ou=system")
                .managerPassword("secureldappassword")
                .managerDn("uid=admin,ou=system");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().formLogin();
    }
    */



}
