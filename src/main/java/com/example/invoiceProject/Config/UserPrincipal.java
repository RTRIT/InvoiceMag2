package com.example.invoiceProject.Config;

import com.example.invoiceProject.Model.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private final User user;

    // Constructor
    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Trả về collection có đối tượng extends từ GrantedAuthority
        //Convert user roles into GrantedAuthority objects
        return user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


}
