package com.example.invoiceProject.Config;


import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import com.example.invoiceProject.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{


    private User user;

    public CustomUserDetails(User user)
    {
        this.user = user;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthorityList =	new SimpleGrantedAuthority(user.getRole().toString());

        return List.of(simpleGrantedAuthorityList);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
