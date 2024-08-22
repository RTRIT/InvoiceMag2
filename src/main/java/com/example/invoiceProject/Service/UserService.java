package com.example.invoiceProject.Service;


import com.example.invoiceProject.Exception.ApplicationException;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;


    public User login(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new CustomException("Email or Password is not valid");
        }
        if(user.getPassword()!=password){
            throw new CustomException("Email or Password is not valid");
        }
        return user;
    }

    @Transactional
    public void register(String email, String password){
        //Check email existed
        User user = userRepository.findByEmail(email);
        if(user!=null){
            throw new CustomException("Email existed!");
        }

        Role roleUser = roleRepository.findByRoleName("USER");
        User newUser = new User(email, password, roleUser);
        userRepository.save(newUser);
    }

    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //Get list user
    public List<User> getListUser(){
        return userRepository.findAll();
    }

    //Get user by id
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User not found"));
    }

    public Optional<User> getUserByUsername(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }
    public void updateUser(String email, String password, int id) {
        userRepository.updateUserById(email, password, id);
    }

}
