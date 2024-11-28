package com.example.invoiceProject.Config;

import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userServ;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user =	userServ.getUserByEmail1(userName);


        if(user == null)
        {
            throw new UsernameNotFoundException("User not Found !!");
        }
        else {
            return new CustomUserDetails(user);
        }

    }
}
