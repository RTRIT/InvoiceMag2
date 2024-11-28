package com.example.invoiceProject.DTO.requests;

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
    String password;
    String firstName;
    String lastName;
//    Long departmentID;

}
