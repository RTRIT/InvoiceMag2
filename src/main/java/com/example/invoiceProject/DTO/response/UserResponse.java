package com.example.invoiceProject.DTO.response;


import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    String id;
    String email;
    String firstName;
    String lastName;
    String department;
}
