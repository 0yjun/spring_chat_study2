package com.springchatstudy2.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springchatstudy2.domain.auth.dto.RequestLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
//            RequestLogin cred = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(
//                            cred.getEmail(),
//                            cred.getPwd(),
//                            new ArrayList<>()
//                    );
//            log.info("AuthenticationFilter: attemptAuthentication password->"+cred.getPwd());
//            log.info("AuthenticationFilter: attemptAuthentication authtoken->"+authenticationToken.toString());
            log.info("AuthenticationFilter: attemptAuthentication password->");

            return super.attemptAuthentication(request, response);
        } catch (Exception e) {
            //TODO CUSTOM EXCEPTION
            throw new RuntimeException(e);
        }

    }
}
