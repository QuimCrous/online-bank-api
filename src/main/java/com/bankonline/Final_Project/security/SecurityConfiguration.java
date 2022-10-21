package com.bankonline.Final_Project.security;

import com.bankonline.Final_Project.Service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/admin/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/account-holder/**").hasRole("USER")
                .mvcMatchers(HttpMethod.PATCH,"/account-holder/**").hasRole("USER")
                .mvcMatchers(HttpMethod.POST,"/account-holder/**").hasRole("USER")
                .mvcMatchers(HttpMethod.PUT,"/account-holder/**").hasRole("USER");

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }
}
