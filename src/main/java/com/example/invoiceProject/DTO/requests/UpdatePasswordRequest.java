package com.example.invoiceProject.DTO.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordRequest {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "New password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no spaces")
    private String newPassword;

    @NotBlank(message = "Confirmation password is required")
    private String confirmPassword;


    // Custom method to check if newPassword matches confirmPassword
    public boolean isPasswordMatching() {
        return this.newPassword != null && this.newPassword.equals(this.confirmPassword);
    }
}
