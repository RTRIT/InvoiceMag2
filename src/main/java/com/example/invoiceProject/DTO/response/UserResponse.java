package com.example.invoiceProject.DTO.response;


import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Model.Role;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    UUID id;
    String email;
    String firstName;
    String lastName;
    Department department;
    List<Role> roles;
}
