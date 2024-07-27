package com.example.invoiceProject.Service;


import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Handle business logic
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User authenticate(String username, String password){
        return userRepository.authenticate(username, password);
    }

    public int register(String username, String password){
        return userRepository.register(username, password);
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }



}
