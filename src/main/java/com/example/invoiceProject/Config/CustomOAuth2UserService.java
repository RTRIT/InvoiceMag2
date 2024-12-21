package com.example.invoiceProject.Config;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.JwtService.JwtService;

import org.hibernate.Hibernate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public CustomOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String firstName = oauth2User.getAttribute("given_name");
        String lastName = oauth2User.getAttribute("family_name");

        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCreatedAt(new Date());
            user.setPassword("12345678");

//            Role defaultRole = roleRepository.findByRoleName("ROLE_USER");
//            user.setRoles(List.of(defaultRole));

            userRepository.save(user);
        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userRepository.save(user);
        }


        String token = jwtService.generateToken(user);

        // Trả về DefaultOAuth2User với thông tin người dùng và các thuộc tính tùy chỉnh
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oauth2User.getAttributes(),
                "email"
        );
    }
}
