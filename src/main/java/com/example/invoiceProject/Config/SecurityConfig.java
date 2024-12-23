package com.example.invoiceProject.Config;

//import com.example.invoiceProject.Config.Security.FilterChain.JwtFilter;

//import com.example.invoiceProject.Config.Security.Authentication_Provider.DaoAuthenticationProvider;
import com.example.invoiceProject.Config.Security.Authentication_Provider.CookieBearerTokenResolver;
import com.example.invoiceProject.Config.Security.Authentication_Provider.JwtFilter;
import com.example.invoiceProject.DTO.requests.LogoutRequest;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.AuthenticateService;
import com.example.invoiceProject.Service.JwtService.JwtService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Value("${jwt.secret}")
    private String SIGNER_KEY;



    @Autowired
    JwtService jwtService;

    @Autowired
    private  CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private ModelMapper mapper;

    private final String[] PUBLIC_ENDPOINTS = {
//            "/api/login", "/user/register",
//            "/jwt/createJwt", "/jwt/validateJwt",
//            "/auth/token", "/auth/introspect",
//            "/auth/logout", "/auth/refresh",
//            "auth/sent", "/test" ,
            "/login/**","/logout",
            "/login/forgot-password",
//             "/product/**","/vendor/**",
//            "/department/**","/fragments/**",
            "/favicon.ico" ,
            "/user/changePassword/**","/user/updatePassword/**",
            "/payment/vnp_ipn/**",
            "/payment/returnPaymentUrl/**",
            "/oauth2/**"

    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserRepository userRepository) throws Exception {




        http.authorizeHttpRequests(request ->
//                request.anyRequest().permitAll())
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated())
//                .csrf(csrf -> csrf.disable())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // To not create session
                );
        http
                .logout( (logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Custom behavior after logout
                            try{
                                String token = null;

                                if(request.getCookies() != null){
                                    for(Cookie cookie : request.getCookies()){
                                        if(cookie.getName().equals("accessToken")){
                                            token = cookie.getValue();
                                        }
                                    }
                                }
                                LogoutRequest logoutRequest = new LogoutRequest();
                                logoutRequest.setToken(token);
                                System.out.println("Token is in spring sec cofig: "+token);
                                authenticateService.logout(logoutRequest);
                            }catch (ParseException parseException){
                                throw new CustomException(parseException.getMessage());
                            }catch (JOSEException joseException){
                                throw new CustomException(joseException.getMessage());
                            }
//                            response.setStatus(HttpServletResponse.SC_OK);
//                            response.getWriter().write("Logout successful!");
                            response.sendRedirect("/login");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("accessToken")
                        .clearAuthentication(true)

                )
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
                            String email = oAuth2User.getAttribute("email");
                            User user = userRepository.getUserByEmail(email);
                            String accessToken = jwtService.generateToken(user);
                            Cookie cookie = new Cookie("accessToken", accessToken);
                            cookie.setHttpOnly(true);
                            cookie.setSecure(true);
                            cookie.setPath("/");
                            cookie.setMaxAge(60 * 60); // 1 giờ
                            response.addCookie(cookie);
                            response.sendRedirect("/dashboard");
                        }));
//
//        //configures Spring Security to use OAuth 2.0 Resource Server for authentication
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt( jwtConfigurer ->
                        jwtConfigurer
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .bearerTokenResolver(cookieBearerTokenResolver())
        )
        ;
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//        http.exceptionHandling()
//                .accessDeniedPage("/access-denied");
        http.exceptionHandling((exception)-> exception.accessDeniedPage("/error/accessDenied"));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define the password encoder, BCryptPasswordEncoder is commonly used for hashing passwords
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }


    //config cors (as default spring sec enable it)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        List<String> setAllowedHeader= new ArrayList<>();
        setAllowedHeader.add("Authorization");
        setAllowedHeader.add("Content-Type");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE", "PUT", "UPDATE", "OPTIONS"));
        configuration.setAllowedHeaders(setAllowedHeader);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


//    @Bean
//    BearerTokenResolver bearerTokenResolver()

    HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));




    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    public BearerTokenResolver cookieBearerTokenResolver(){
        return new CookieBearerTokenResolver("accessToken");
    }


}