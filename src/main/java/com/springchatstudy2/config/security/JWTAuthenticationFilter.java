package com.springchatstudy2.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;


    public JWTAuthenticationFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getTokenFromReqest(request);
        String username = jwtProvider.extractusername(token);

        try{
            jwtProvider.validateToken(token);
            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(username,null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch(Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
            return;
        }
        log.info("username: "+username);
        log.info("jwt 성공");
        filterChain.doFilter(request, response);
    }

    private String getTokenFromReqest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
