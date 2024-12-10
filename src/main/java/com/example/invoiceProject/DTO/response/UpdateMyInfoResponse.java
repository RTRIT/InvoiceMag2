package com.example.invoiceProject.DTO.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMyInfoResponse {
//    String emailUpdate;
    String firstname;
    String lastname;
}
