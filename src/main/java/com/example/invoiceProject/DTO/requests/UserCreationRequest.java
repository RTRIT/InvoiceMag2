package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    // Mặc định có role User khi được tạo
    @Email(message = "Email is not validated!")
    String email;
    @Size(min = 8, message = "Password is at least 8 character!")
    String password;
    Department department;
    String firstName;
    String lastName;


}
