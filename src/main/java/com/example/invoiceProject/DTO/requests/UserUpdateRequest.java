package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Model.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String email;
    Long department;
    List<Role> roles;
//    Long departmentID;

}
