package com.nursery.securityconfig;

//import java.security.SignatureException;
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.UnsupportedJwtException;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${nursery.app.jwtSecret}")
    private String jwtSecret;

    @Value("${nursery.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject((String) (authentication.getName()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Map<String, Object> validateJwtToken(String authToken) {
    	Map<String, Object> map = new HashMap<>();
    	String msg ="Internal Server Error";
    	String statusCode = "500";
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            statusCode = "200";
            msg = "Token Verified";
            //return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
            statusCode = "400";
            msg = "Invalid JWT signature -> Message: {} "+e.getMessage();
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
            statusCode = "400";
            msg = "Invalid JWT token -> Message: {}"+e.getMessage();
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
            statusCode = "400";
            msg = "Expired JWT token -> Message: {}"+e.getMessage();
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
            statusCode = "400";
            msg = "Unsupported JWT token -> Message: {}"+e.getMessage();
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
            statusCode = "400";
            msg = "JWT claims string is empty -> Message: {}"+e.getMessage();
        }
        
        map.put("statusCode", statusCode);
        map.put("message", msg);

        return map;
    }

}
