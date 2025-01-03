package com.example.invoiceProject.DTO.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivilegeRequest {
    long id;
    String name;
    String description;
}
