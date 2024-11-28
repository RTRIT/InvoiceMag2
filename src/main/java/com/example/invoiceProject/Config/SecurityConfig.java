package com.example.invoiceProject.Config;

//import com.example.invoiceProject.Config.Security.FilterChain.JwtFilter;

//import com.example.invoiceProject.Config.Security.Authentication_Provider.DaoAuthenticationProvider;
import com.example.invoiceProject.Service.JwtService.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
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
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomJwtDecoder customJwtDecoder;


    private final String[] PUBLIC_ENDPOINTS = {
            "/api/login", "/api/register",
            "/jwt/createJwt", "/jwt/validateJwt",
            "/auth/token", "/auth/introspect",
            "/auth/logout", "/auth/refresh",
            "auth/sent", "/test" , "/login" , "/dashboard"  };
//
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
//                request.anyRequest().permitAll())
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated())

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // To not create session

                );

//
//        configures Spring Security to use OAuth 2.0 Resource Server for authentication
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt( jwtConfigurer ->
                        jwtConfigurer
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())

                )
        )
        ;


        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // Xác thực người dùng và phân quyền
//                .authorizeHttpRequests(request ->
//                        request.requestMatchers("/login", "/register", "/public/**").permitAll() // Các trang không cần xác thực
//                                .anyRequest().authenticated()) // Các trang còn lại yêu cầu xác thực
//                // Cấu hình form login
//                .formLogin(form ->
//                        form
//                                .loginPage("/login") // Trang đăng nhập
//                                .loginProcessingUrl("/login") // Địa chỉ xử lý form login
//                                .defaultSuccessUrl("/dashboard", true) // Trang chính sau khi đăng nhập thành công
//                                .permitAll())
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/logout") // Địa chỉ logout
//                                .logoutSuccessUrl("/login") // Chuyển về trang login sau khi đăng xuất
//                                .permitAll())
//                // Tắt CSRF (thường không cần khi sử dụng JWT, nhưng với form login có thể cần bật lại nếu cần)
//                .csrf(csrf -> csrf.disable())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
//                        jwtConfigurer -> jwtConfigurer
//                                .decoder(customJwtDecoder)
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // Sử dụng session nếu cần
////                .httpBasic().disable(); // Tắt HTTP Basic Authentication
//
//        return http.build();
//    }



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

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}