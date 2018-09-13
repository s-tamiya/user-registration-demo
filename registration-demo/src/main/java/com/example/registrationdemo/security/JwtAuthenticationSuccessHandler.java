package com.example.registrationdemo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@ComponentScan("security")
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil) {
        super();
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        System.out.println("onAuthenticationSuccess : ");

        setToken(response, jwtTokenUtil.generateToken(authentication));
        response.setStatus(HttpStatus.OK.value());
        clearAuthenticationAttribute(request);
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
