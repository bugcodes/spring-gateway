//package com.bugcoder.config;
//
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.annotation.Resource;
//
///**
// * @author zbj
// * @date 2022/12/19
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Resource
//    private CustomAuthenticationProvider authProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
//                .antMatchers("/rsa/publicKey").permitAll().anyRequest().authenticated();
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
//        return authenticationManagerBuilder.build();
//    }
//}
