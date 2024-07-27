package com.example.invoiceProject.Service;


import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//Handle business logic
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User authenticate(String username, String password){
        return userRepository.authenticate(username, password);
    }

    public void register(String username, String password){
        userRepository.register(username, password);
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }


    public void updateUser(String username, String password, String fullName ,Long id) {
        userRepository.updateUserById(username, password, fullName ,id);
    }
}
