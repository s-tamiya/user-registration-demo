package com.example.registrationdemo.security;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Algorithm algorithm;
    
    public JwtAuthenticationSuccessHandler(Algorithm algorithm) {
        Objects.requireNonNull(algorithm, "algorithm must not be null");
        this.algorithm = algorithm;
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        setToken(response, generateToken(authentication));
        response.setStatus(HttpStatus.OK.value());
        clearAuthenticationAttribute(request);
    }
    
    private static final Long EXPIRATION_TIME = 1000L * 60L * 10L;
    
    private String generateToken(Authentication auth) {
      LoginUser loginUser = (LoginUser) auth.getPrincipal();
      Date issueAt = new Date();
      Date notBefore = new Date(issueAt.getTime());
      Date expireDate = new Date(issueAt.getTime() + EXPIRATION_TIME);
      
      String token = JWT.create()
              .withIssuedAt(issueAt)
              .withNotBefore(notBefore)
              .withExpiresAt(expireDate)
              .withSubject(loginUser.getUser().getUserId().toString())
              .sign(this.algorithm);
      
      return token;
    }
    
    private void setToken(HttpServletResponse response, String token) {
      response.setHeader("Authorization", token);
    }

    private void clearAuthenticationAttribute(HttpServletRequest request) {
      HttpSession session = request.getSession(false);

      if (session == null) {
        return;
      }

      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
