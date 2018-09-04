package com.example.registrationdemo.security;

import static com.example.registrationdemo.security.Constants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    @Override
    protected void configure (HttpSecurity http) throws Exception {
       http
           .authorizeRequests()
           .mvcMatchers("/user/register")
             .permitAll()
           .anyRequest()
             .authenticated()
         .and()
           // exception
           .exceptionHandling()
             .authenticationEntryPoint(unauthrizedHandler())
             //.accessDeniedHandler(accessDeniedHandler())
           // login
         .and()
           .formLogin()
             .loginProcessingUrl("/login").permitAll()
               .usernameParameter("email")
               .passwordParameter("password")
             .successHandler(authenticationSuccessHandler())
             .failureHandler(authenticationFailureHandler())
         .and()
           .logout()
             .logoutSuccessHandler(logoutSuccessHandler())
         .and()
           .csrf()
           .disable()
           .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
           .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           ;

       ;
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth,
        @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailService,
        PasswordEncoder passwordEncoder
    ) throws Exception {
      auth.eraseCredentials(true)
          .userDetailsService(userDetailService)
          .passwordEncoder(passwordEncoder);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JwtAuthenticationEntryPoint unauthrizedHandler() {
        return new JwtAuthenticationEntryPoint();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() throws Exception {
        return new JwtAuthenticationTokenFilter(algorithm);
    }

    private JwtAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new JwtAuthenticationSuccessHandler(algorithm);
    }

    private JwtAuthenticationFailureHandler authenticationFailureHandler() throws Exception {
        return new JwtAuthenticationFailureHandler();
    }
}
