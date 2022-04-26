package com.finnegan.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class AuthenticationService {
    static final long EXPIRATION_TIME = 86_400_000; // 1 day in milliseconds
    static final String SIGN_IN_KEY = "SecretKey";
    static final String PREFIX = "Bearer";

    // add token to auth header
    static public void addToken(HttpServletResponse res, String username) {
        var JwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SIGN_IN_KEY)
                .compact();
        res.addHeader("Authorization", PREFIX + " " + JwtToken);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    // get token from authorization header
    static public Authentication getAuthentication(HttpServletRequest req) {
        var token = req.getHeader("Authorization");
        if (token != null) {
            var user = Jwts.parser()
                    .setSigningKey(SIGN_IN_KEY)
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null,
                        Collections.emptyList());
            }

        }
        return null;
    }

}
