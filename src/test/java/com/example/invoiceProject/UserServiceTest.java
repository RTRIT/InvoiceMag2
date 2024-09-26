package com.example.invoiceProject;

import com.example.invoiceProject.Model.Role;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import com.example.invoiceProject.Service.RoleService;
import com.example.invoiceProject.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;


    private User user;
    private Role role;

//    user = User
    @BeforeEach
    void setup(){

        role =new Role();
        role.setRoleName("USER");

        user = new User();
//        user.setId(1L); // Set the user ID
        user.setEmail("newUser");
        user.setRole(role);
        user.setPassword("123456");

        // Mocking roleRepository behavior
        when(roleRepository.findByRoleName("USER")).thenReturn(role);

        // Mocking userRepository behavior
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void userShouldBeSaved() {
        User savedUser = userService.register(user);

        // Verify that userRepository.save was called once
        verify(userRepository, times(1)).save(user);

        // Assertions to check that the saved user has the expected values
        assertNotNull(savedUser);
        assertEquals("newUser", savedUser.getEmail());
        assertEquals("USER", savedUser.getRole().getRoleName());
    }
}
