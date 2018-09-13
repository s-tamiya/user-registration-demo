package com.example.registrationdemo.security;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenUtil {

    public static final long expirationMs = 5*60*60;
    public static final String secretKey = "secret_key";
    public static final String jwtTokenPrefix = "Bearer ";
    public static final String jwtHeaderStging = "Authorization";

    private final Algorithm algorithm = Algorithm.HMAC256(secretKey);

    public String generateToken(Authentication auth) throws JWTCreationException {
            LoginUser loginUser = (LoginUser) auth.getPrincipal();
            Date issueAt = new Date();
            Date notBefore = new Date(issueAt.getTime());
            Date expireDate = new Date(issueAt.getTime() + expirationMs);

            System.out.println("onAuthenticationSuccess : " + (loginUser != null));

            String token = JWT.create()
                .withIssuedAt(issueAt)
                .withNotBefore(notBefore)
                .withExpiresAt(expireDate)
                .withSubject(loginUser.getUser().getUserId().toString())
                .sign(algorithm);

            System.out.println("onAuthenticationSuccess : " + (token != null));

            System.out.println(token);

            return token;
      }

    public String resolveToken(HttpServletRequest request) {
        Objects.requireNonNull(request, "request must not be null");

        String token = null;
        String header = request.getHeader(jwtHeaderStging);
        if (header != null && header.startsWith(jwtTokenPrefix)) {
            token = header.replace(jwtTokenPrefix, "");
        }
        return token;
    }

    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifire = JWT.require(algorithm).build();
        return verifire.verify(token);
    }
}
