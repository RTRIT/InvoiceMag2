package com.example.invoiceProject.DTO.requests;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.logging.Level;

@Data
@AllArgsConstructor

@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMyInfoRequest {
//    String emailUpdate;
    String firstname;
    String lastname;
}
