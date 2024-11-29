package com.springchatstudy2.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springchatstudy2.domain.auth.dto.AuthDto;
import com.springchatstudy2.domain.auth.dto.RequestLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        RequestLogin creds = null;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            logger.info("email: {} " + creds.getEmail());
            logger.info("password: {} " + creds.getPassword());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getEmail(),
                    creds.getPassword(),
                    new ArrayList<>()
            );
            logger.info("authToeken: {} " + authToken.toString());
            //return super.attemptAuthentication(request, response);
            return this.authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        log.info("login failed");
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request
            , HttpServletResponse response
            , FilterChain chain
            , Authentication authResult
    ) throws IOException, ServletException {
        log.info("로그인 성공");
        AuthDto authDto = (AuthDto) authResult.getPrincipal();
        String token = JWTProvider.createToken(authDto.getEmail());
        response.addHeader("token",token);
        response.addHeader("email", authDto.getEmail());
        response.setStatus(HttpStatus.OK.value());

        //super.successfulAuthentication(request, response, chain, authResult);
    }
}
