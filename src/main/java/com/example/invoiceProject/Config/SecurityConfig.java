package com.example.invoiceProject.Config;

import com.example.invoiceProject.Service.UserService;
import com.example.invoiceProject.Util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // Set the userDetailsService and password encoder for Spring Security
////        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define the password encoder, BCryptPasswordEncoder is commonly used for hashing passwords
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        // Expose the AuthenticationManager as a bean for injection elsewhere
//        return super.authenticationManagerBean();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // Disable CSRF as we're using JWT tokens which are immune to CSRF attacks
//        http.csrf().disable()
//                // Authorize requests
//                .authorizeRequests()
//                // Allow unauthenticated access to the login endpoint
//                .antMatchers("/authenticate").permitAll()
//                // All other requests need to be authenticated
//                .anyRequest().authenticated()
//                .and()
//                // Set session management to stateless, as JWTs handle authentication state
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // Add the JWT filter before the UsernamePasswordAuthenticationFilter in the security chain
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}