package com.example.invoiceProject.Service;


import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
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



    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User not found"));
    }
    public List<User> getListUser(){
        return userRepository.findAll();
    }

    public User login(User loginForm){
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new CustomException("Email or password is not valid"));
        Optional.of(user)
                .filter(u -> u.getPassword().equals(loginForm.getPassword()))
                .orElseThrow( () -> new CustomException("Email or password is not valid"));
        return user;
    }

    @Transactional
    public User register(User registerForm){

        Role roleUser = roleRepository.findByRoleName("USER");
        User newUser = new User(registerForm.getEmail(), registerForm.getPassword(), roleUser);
        return userRepository.save(newUser);
    }

    @Transactional
    public void delete(String username){
        Optional<User> user = userRepository.findByEmail(username);
        userRepository.delete(user.get());
    }

    public Optional<User> getUserByUsername(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    public void update(User updateForm) {
        User user = userRepository.findById(updateForm.getId()).get(); // Dung get nay khong on

        user.setEmail(updateForm.getEmail());
        user.setPassword(updateForm.getPassword());
        user.setRole(updateForm.getRole());

        userRepository.save(user);
    }
    public boolean userExist(String email){
        return userRepository.existUser(email);
    }

}
