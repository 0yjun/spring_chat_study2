package com.springchatstudy2.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springchatstudy2.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JWTProvider {
    private static String secretKey;
    private static String refreshSecretKey;
    private static long tokenTimeForMinute;
    private static long refreshTokenTimeForMinute;

    @Value("${token.secret-key}")
    public void setSecretKey(String secretKey) {
        JWTProvider.secretKey = secretKey;
    }

    @Value("${token.refresh-secret-key}")
    public void setRefreshSecretKey(String refreshSecretKey) {
        JWTProvider.refreshSecretKey = refreshSecretKey;
    }

    @Value("${token.token-time}")
    public void setTokenTime(long tokenTime) {
        JWTProvider.tokenTimeForMinute = tokenTime;
    }

    @Value("${token.refresh-token-time}")
    public void setRefreshTokenTime(long refreshTokenTime) {
        JWTProvider.tokenTimeForMinute = refreshTokenTime;
    }

    public static String createToken(String name){
        return JWT.create()
                .withSubject(name)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenTimeForMinute * Constants.ON_MINUTE_TO_MILLIS))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public void validateToken(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        Date expireDate = decodedJWT.getExpiresAt();
        //만료일자 확인
        if(expireDate.before(new Date())){
            //TODO
            throw new RuntimeException("만료");
        }

        try{
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
        }catch(SignatureVerificationException e){
            //TODO
            throw new RuntimeException("서명");
        } catch (JWTVerificationException e){
            //TODO
            throw new RuntimeException("검증");
        }

    }
    public String extractusername(String token) {
        return decodeJWT(token).getSubject();
    }

    public DecodedJWT decodeJWT(String token){
        return JWT.decode(token);
    }


}
