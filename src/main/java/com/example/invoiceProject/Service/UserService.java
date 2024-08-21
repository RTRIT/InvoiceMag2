package com.example.invoiceProject.Service;


import com.example.invoiceProject.Exception.ApplicationException;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
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
    private RoleService roleService;


    public User login(String email, String password){
        return userRepository.authenticate(email, password);
    }
    @Transactional
    public void register(String email, String password){
        //Kiểm tra email đã tồn tại chưa

        if(checkEmailExist(email)!=null){
            throw new CustomException("Email existed already!");
        }
        Role role = roleService.getRoleByName("User");
        userRepository.register(email, password, role.getId());
    }


    public Optional<User> getUserByUsername2(String email) {
        return Optional.ofNullable(userRepository.getUserByUsername(email));
    }



    public void updateUser(String email, String password, int id) {
        userRepository.updateUserById(email, password, id);
    }

    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    //Get list user
    public List<User> getListUser(){
        return userRepository.getListUser();
    }

    //Get user by id
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User not found"));
        return user;
    }

    //Check email exist
    public User checkEmailExist(String email){
        return userRepository.existsByEmail(email);
    }
}
