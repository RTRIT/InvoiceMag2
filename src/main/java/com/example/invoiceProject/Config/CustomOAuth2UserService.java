
package com.example.invoiceProject.Config;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Nếu cần xử lý vai trò mặc định

    public CustomOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Lấy thông tin người dùng từ Google
        OAuth2User oauth2User = super.loadUser(userRequest);

            // Lấy các thuộc tính từ Google
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String firstName = oauth2User.getAttribute("given_name");
        String lastName = oauth2User.getAttribute("family_name");

        // Kiểm tra xem tài khoản đã tồn tại hay chưa
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            // Nếu tài khoản chưa tồn tại, tạo tài khoản mới
            user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCreatedAt(new Date());

            // Mặc định gán password rỗng hoặc một giá trị mặc định (không sử dụng cho OAuth2)
            user.setPassword("12345678");

            // Gán vai trò mặc định cho tài khoản mới
            Role defaultRole = roleRepository.findByRoleName("ROLE_USER"); // Đảm bảo vai trò này tồn tại
            user.setRoles(List.of(defaultRole));

            userRepository.save(user);
        }

        // Trả về DefaultOAuth2User (bao gồm thông tin vai trò và thuộc tính)
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oauth2User.getAttributes(),
                "email"
        );
    }
}
