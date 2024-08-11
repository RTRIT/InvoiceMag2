package com.example.invoiceProject.Service;


import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User authenticate(String email, String password){
        return userRepository.authenticate(email, password);
    }

    public void register(String email, String password, Long role){
        userRepository.register(email, password, role);
    }

    public User getUserByUsername(String email){
        return userRepository.getUserByUsername(email);
    }


    public void updateUser(String email, String password, int id) {
        userRepository.updateUserById(email, password, id);
    }

    public void deleteUser(Long id){
        userRepository.deleteUserById(id);
    }

    public List<User> getListUser(){
        return userRepository.getListUser();
    }


}
