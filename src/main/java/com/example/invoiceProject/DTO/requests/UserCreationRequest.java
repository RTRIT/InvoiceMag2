package com.example.invoiceProject.DTO.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreationRequest {

    @Email(message = "Email is not validated!")
    private String email;
    @Size(min = 8, message = "Password is at least 8 character!")
    private String password;

}
