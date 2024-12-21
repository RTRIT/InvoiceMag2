package com.example.invoiceProject.DTO.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailVerificationResponse {
    private boolean format_valid;
    private boolean smtp_check;

    // Getters và Setters
    public boolean isFormatValid() {
        return format_valid;
    }

    public void setFormatValid(boolean format_valid) {
        this.format_valid = format_valid;
    }

    public boolean isSmtpCheck() {
        return smtp_check;
    }

    public void setSmtpCheck(boolean smtp_check) {
        this.smtp_check = smtp_check;
    }
}
