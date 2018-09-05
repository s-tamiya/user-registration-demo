package com.example.registrationdemo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@ComponentScan("security")
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //private final Algorithm algorithm;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /*
    @Value("${security.token.expiration.time:1000L}")
    private final Long EXPIRATION_TIME = 1000L * 60L * 10L;
    */

    public JwtAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        System.out.println("onAuthenticationSuccess : ");

        setToken(response, jwtTokenUtil.generateToken(authentication));
        response.setStatus(HttpStatus.OK.value());
        clearAuthenticationAttribute(request);
    }

    /*private String generateToken(Authentication auth) {
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

      System.out.println(EXPIRATION_TIME);

      return token;
    }*/

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
