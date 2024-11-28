//package com.example.invoiceProject.Config;
//
//import com.example.invoiceProject.DTO.response.UserResponse;
//import com.example.invoiceProject.Service.JwtService.JwtService;
//import com.example.invoiceProject.Service.UserService;
//import com.nimbusds.jose.JOSEException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.text.ParseException;
//
//
//@Component
//public class JwtAuthFilter extends OncePerRequestFilter {
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String token = null;
//        String username = null;
//
//        if(request.getCookies() != null){
//            for(Cookie cookie: request.getCookies()){
//                if(cookie.getName().equals("accessToken")){
//                    token = cookie.getValue();
//                }
//            }
//        }
//
//        try {
//            jwtService.verifyToken(token, true);
//        } catch (JOSEException e) {
//            throw new RuntimeException(e);
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        if(token == null){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            username = jwtService.getSubjectFromToken(token);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        } catch (JOSEException e) {
//            throw new RuntimeException(e);
//        }
//
//        if(username != null){
//            CustomUserDetails userDetails = userService.getUserByEmail(username);
//            if(userDetails != null){
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}
