package com.springchatstudy2.config.security;

import com.springchatstudy2.domain.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final AuthService authService;
    private final JWTProvider jwtProvider;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(
            AuthService authService,
            JWTProvider jwtProvider, AuthenticationConfiguration authenticationConfiguration) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests(authorize -> {
//            authorize.requestMatchers("/login").permitAll();
//            authorize.anyRequest().authenticated();
//        });

        http.addFilterBefore(
                new LoginFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class
        );

        http.addFilterBefore(
                new JWTAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
    
    /**
     * 필터 예외처리
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)->web.ignoring().requestMatchers(HttpMethod.POST,"/user");
    }
   
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager manager = this.authenticationConfiguration.getAuthenticationManager();
        return manager;
    }

//
//    public LoginFilter getLoginFilter() throws Exception {
//        return new LoginFilter(authenticationManager());
//    }
//
//
//    public JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
//        return new JWTAuthenticationFilter(jwtProvider);
//    }
}