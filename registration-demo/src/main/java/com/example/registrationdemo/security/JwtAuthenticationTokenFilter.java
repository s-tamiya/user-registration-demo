package com.example.registrationdemo.security;

import static com.example.registrationdemo.security.Constants.*;

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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.registrationdemo.repository.UserRepository;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;


    private final Algorithm algorithm;

    public JwtAuthenticationTokenFilter(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);
        System.out.println(token);
        if (token != null) {

            try {
                authorication(verifyToken(token));
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

    private String resolveToken(HttpServletRequest request) {
        String token = null;
        String header = request.getHeader(HEADER_STRING);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            token = header.replace(TOKEN_PREFIX, "");
        }
        return token;
    }

    private DecodedJWT verifyToken(String token) {
        JWTVerifier verifire = JWT.require(algorithm).build();
        return verifire.verify(token);
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
