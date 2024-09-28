package com.example.invoiceProject;

import com.example.invoiceProject.DTO.requests.UserCreationRequest;
import com.example.invoiceProject.DTO.response.UserResponse;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserResponse userResponse;
    @InjectMocks
    private UserService userService;


    private User user;
    private Role role;

//    user = User
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void userShouldBeSaved() {
        // Arrange
        UserCreationRequest request = new UserCreationRequest();
        request.setEmail("Tringuyen@gmail.com");
        request.setPassword("123456789");

        Role role = new Role();
        role.setRoleName("USER");

        User userEntity = new User();
        userEntity.setEmail("Tringuyen@gmail.com");
        userEntity.setPassword("123456789");
        userEntity.setRole(role);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("Tringuyen@gmail.com");
        userResponse.setFirstName("Tri");
        userResponse.setLastName("Nguyen");

        // Mock the repository and mapper calls
        when(roleRepository.findByRoleName("USER")).thenReturn(role);
        when(mapper.map(request, User.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.map(userEntity, UserResponse.class)).thenReturn(userResponse);

        // Act
        UserResponse result = userService.createUser(request);

        // Assert
        assertNotNull(result);
        assertEquals("Tringuyen@gmail.com", result.getEmail());
        assertEquals("Tri", result.getFirstName());
        assertEquals("Nguyen", result.getLastName());
    }
}
