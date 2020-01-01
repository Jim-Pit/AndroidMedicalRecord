package com.iatrikhplhroforia.emf.authJwt;

// JWT security code is taken from https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world

// to gain access to the systems endpoints we must make a POST request at /auth with the user's username and password
// the request body must be like:
// {
//	"email":"XXX"
//	,"password":"YYY"
// }
// the /auth endpoint is handled by JwtAuthController

import com.google.firebase.auth.hash.Scrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecuritas extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    private JwtReqFilter jwtRequestFilter;
    @Autowired
    private JwtUDetailsService uDetailsServiceJWT;  // class that implements UserDetailsService, to confirm UserDetails

    @Bean   // this is needed to authenticate a user in AuthController
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // customize auth using UserDetailsService interface
        auth.userDetailsService(uDetailsServiceJWT).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/auth").permitAll();

        // all other requests need to be authenticated

        http.authorizeRequests().and()
        .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint).and()

                /* make sure we use stateless session; session won't be used to store user's state. */

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    PasswordEncoder encoder(){
//        return new SCryptPasswordEncoder();
//    }
}
