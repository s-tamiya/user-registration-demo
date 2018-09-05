package com.example.registrationdemo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.registrationdemo.repository.UserRepository;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtTokenUtil.resolveToken(request);
        System.out.println(token);
        if (token != null) {

            try {
                authorication(jwtTokenUtil.verifyToken(token));
            } catch (JWTVerificationException e) {
                logger.error("an error occured during verify token", e);
                SecurityContextHolder.clearContext();
                response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }

        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        filterChain.doFilter(request, response);
    }

    private void authorication(DecodedJWT jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        userRepository.findById(userId).ifPresent(user -> {
            LoginUser loginUser = new LoginUser(user);
            SecurityContextHolder.getContext()
              .setAuthentication(
                  new UsernamePasswordAuthenticationToken(loginUser, null, null)
                );
        });
    }
}
